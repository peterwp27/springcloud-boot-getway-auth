package com.nriet.framework.api.netcdf;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nriet.framework.api.netcdf.entity.NcData;
import com.nriet.framework.api.netcdf.entity.NcParam;
import com.nriet.framework.api.netcdf.entity.NcParam1D;

import ucar.ma2.DataType;
import ucar.nc2.Attribute;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;

public class NetcdfFileReader {
	private final static Logger logger = LoggerFactory.getLogger(NetcdfFileReader2.class);
	/**
	 * 多线程同时读取NC文件
	 * @param params
	 * 				多个参数组，[{path:"nc文件路径",params:"读取参数集"}]
	 * @param PoolSize
	 * 				支持的最大线程数
	 * @return
	 * 			Map<String,Object>
	 */
	public static Map<String,Object> readNcFiles(List<Map<String, Object>> params, int PoolSize) {
		ExecutorService es = Executors.newFixedThreadPool(PoolSize);
//		List<List<NcData>> rslist = new LinkedList<List<NcData>>();
		Map<String,Object> rsMap = new HashMap();
		long start = System.currentTimeMillis();
		for (int i = 0; i < params.size(); i++) {
			final int s = i;
			final Map<String,Object> map = params.get(s);
			es.execute(new Runnable() {
				@Override
				public void run() {
					List<NcData> nclist = readNcFile(map.get("path").toString(),(List<NcParam>)map.get("params"));
					rsMap.put(map.get("path").toString(), nclist);
				}
			});
		}
		es.shutdown();
		while (true) {
			if (es.isTerminated()) {
				long time = System.currentTimeMillis() - start;
				System.out.println("[" + new Date() + "]:" + "NC文件读取结束了，总耗时：" + time + " ms(毫秒)！\n");
				System.out.println("总记录数::::" + rsMap.size());
				break;
			}
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage(),e);
			}
		}
		return rsMap;
	}
	/**
	 * Nc文件读取(共通)
	 * 
	 * @param path
	 *            nc文件路径
	 * @param params
	 *            读取NC相关参数
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<NcData> readNcFile(String path, List<NcParam> params) {
		if (path == null || params == null || params.isEmpty()) {
			return null;
		}
		File file = new File(path);
		if (!file.exists()) {
			return null;
		}

		List<NcData> dataList = new ArrayList<>();
		NetcdfFile ncFile = null;

		try {
			ncFile = NetcdfFile.open(path);
			for (NcParam ncParam : params) {
				if (ncParam == null || (ncParam.getVariableName() == null && ncParam.getVariableNameRegex() == null)) {
					continue;
				}
				
				if(ncParam.getVariableName() != null) {
					Variable v = ncFile.findVariable(ncParam.getVariableName());
					if (v == null) {
						continue;
					}
					readNc(v, ncParam, dataList, ncFile);
				}else if(ncParam.getVariableNameRegex() != null) {
					for(Variable v : ncFile.getVariables()) {
						if(v.getShortName().matches(ncParam.getVariableNameRegex())) {
							readNc(v, ncParam, dataList, ncFile);
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ncFile != null) {
				try {
					ncFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return dataList;
	}
	
	private static void readNc(Variable v, NcParam ncParam, List<NcData> dataList, NetcdfFile ncFile) {
		List<Attribute> attrs = v.getAttributes();
		Map<String, String> attributeMap = new HashMap<>();
		for (Attribute attr : attrs) {
			DataType attrDataType = attr.getDataType();
			if (attrDataType.isString()) {
				attributeMap.put(attr.getShortName(), attr.getStringValue());
			} else if (attrDataType.isNumeric()) {
				attributeMap.put(attr.getShortName(), attr.getNumericValue().toString());
			}
		}

		DataType dataType = v.getDataType();
		int dimSize = 0;
		int[] shapes = v.getShape();
		for (int shape : shapes) {
			if (shape > 1) {
				dimSize++;
			}
		}

		Map<Integer, NcParam1D> v1DParams = ncParam.getV1DParams();
		if (v1DParams == null || v1DParams.isEmpty()) {
			NcData ncData = readVariableData(v, dataType, dimSize, null, null);
			ncData.setVariableName(v.getShortName());
			ncData.setAttributeMap(attributeMap);
			dataList.add(ncData);
		} else {
			StringBuilder specBuilder = new StringBuilder();
			List<Integer> avgNumList = new ArrayList<>();
			for (int i = 0; i < shapes.length; i++) {
				NcParam1D param1D = v1DParams.get(i);
				if (param1D != null) {
					int avgNum = param1D.getAvgNum();
					int cutNum = param1D.getCutNum();
					if (param1D.getStartInt() != null) {
						int startInt = param1D.getStartInt();
						if (startInt < 0) {
							startInt = 0;
						}
						if (startInt > shapes[i]) {
							startInt = shapes[i] - 1;
						}
						specBuilder.append(startInt);
						if (param1D.getEndInt() != null) {
							int endInt = param1D.getEndInt();
							if (endInt < 0) {
								endInt = 0;
							}
							if (endInt > shapes[i]) {
								endInt = shapes[i] - 1;
							}
							specBuilder.append(":").append(endInt);
							if (avgNum == 1 && cutNum > 1) {
								specBuilder.append(":").append(cutNum);
							}
							avgNumList.add(avgNum);
						} else {
							dimSize--;
						}
						specBuilder.append(",");
					} else if (param1D.getStartValue() != null) {
						String v1DName = param1D.getVariable1DName();
						Variable v1d = ncFile.findVariable(v1DName);
						if (v1d == null) {
							return;
						}
						DataType v1dDataType = v1d.getDataType();
						NcData nc1DData = readVariableData(v1d, v1dDataType, 1, null, null);
						int startInt = 0;
						int endInt = 0;
						double tempStart = 9999.0f;
						double tempEnd = 9999.0f;
						if (DataType.FLOAT == v1dDataType) {
							float[] v1dData = (float[]) nc1DData.getData();
							for (int j = 0; j < v1dData.length; j++) {
								float minusStart = Math.abs((float) param1D.getStartValue() - v1dData[j]);
								if (tempStart > minusStart) {
									tempStart = minusStart;
									startInt = j;
								}
								if (param1D.getEndValue() != null) {
									float minusEnd = Math.abs((float) param1D.getEndValue() - v1dData[j]);
									if (tempEnd > minusEnd) {
										tempEnd = minusEnd;
										endInt = j;
									}
								}
							}

						} else if (DataType.DOUBLE == v1dDataType) {
							double[] v1dData = (double[]) nc1DData.getData();
							for (int j = 0; j < v1dData.length; j++) {
								double minusStart = Math.abs((double) param1D.getStartValue() - v1dData[j]);
								if (tempStart > minusStart) {
									tempStart = minusStart;
									startInt = j;
								}
								if (param1D.getEndValue() != null) {
									double minusEnd = Math.abs((double) param1D.getEndValue() - v1dData[j]);
									if (tempEnd > minusEnd) {
										tempEnd = minusEnd;
										endInt = j;
									}
								}
							}
						} else if (DataType.LONG == v1dDataType) {
							long[] v1dData = (long[]) nc1DData.getData();
							for (int j = 0; j < v1dData.length; j++) {
								double minusStart = Math.abs((long) param1D.getStartValue() - v1dData[j]);
								if (tempStart > minusStart) {
									tempStart = minusStart;
									startInt = j;
								}
								if (param1D.getEndValue() != null) {
									double minusEnd = Math.abs((long) param1D.getEndValue() - v1dData[j]);
									if (tempEnd > minusEnd) {
										tempEnd = minusEnd;
										endInt = j;
									}
								}
							}
						}
						if (param1D.getEndValue() != null) {
							specBuilder.append(startInt < endInt ? startInt : endInt).append(":")
									.append(startInt < endInt ? endInt : startInt);
							if (avgNum == 1 && cutNum > 1) {
								specBuilder.append(":").append(cutNum);
							}
							avgNumList.add(avgNum);
							specBuilder.append(",");
						} else {
							specBuilder.append(startInt).append(",");
							dimSize--;
						}
					} else {
						int shape = shapes[i];
						if (shape == 1) {
							specBuilder.append("0,");
						} else {
							specBuilder.append("0:").append(shape - 1).append(",");
						}
					}
				} else {
					int shape = shapes[i];
					if (shape == 1) {
						specBuilder.append("0,");
					} else {
						specBuilder.append("0:").append(shape - 1).append(",");
						avgNumList.add(1);
					}
				}
			}
			if (specBuilder.length() != 0) {
				specBuilder.deleteCharAt(specBuilder.length() - 1);
			}
			String sectionSpec = specBuilder.toString();
			System.out.println("Spec:" + sectionSpec);
			if (dimSize == 0) {
				dimSize = 1;
			}
			NcData ncData = readVariableData(v, dataType, dimSize, sectionSpec, avgNumList);
			ncData.setVariableName(v.getShortName());
			ncData.setAttributeMap(attributeMap);
			dataList.add(ncData);
		}
	}
	

	private static NcData readVariableData(Variable v, DataType dataType, int dimSize, String sectionSpec,
			List<Integer> avgNumList) {
		try {
			if (dimSize == 1) {
				if (DataType.FLOAT == dataType) {
					NcData<float[]> ncData = new NcData<float[]>();
					float[] data = null;
					if (sectionSpec != null && !sectionSpec.isEmpty()) {
						data = (float[]) v.read(sectionSpec).reduce().copyTo1DJavaArray();
						if (avgNumList != null && !avgNumList.isEmpty() && avgNumList.size() == dimSize) {
							int avgNum1 = avgNumList.get(0);
							if (avgNum1 > 1 && avgNum1 < data.length) {
								Double cuts = Double.valueOf(data.length) / avgNum1;
								Double deci = cuts - cuts.intValue();
								int newSize = cuts.intValue();
								if (deci != Double.valueOf(0)) {
									newSize++;
								}
								float[] newData = new float[newSize];
								for (int i = 0; i < newSize; i++) {
									float pieceTotal = 0;
									int count = 0;
									for (int a = i * avgNum1; a < ((i + 1) * avgNum1 > data.length ? data.length
											: (i + 1) * avgNum1); a++) {
										if (data[a] < 999999) {
											pieceTotal = pieceTotal + data[a];
										}
										count++;
									}
									newData[i] = pieceTotal / count;
								}
								data = newData;
							}
						}
					} else {
						data = (float[]) v.read().reduce().copyTo1DJavaArray();
					}
					ncData.setData(data);
					return ncData;
				} else if (DataType.DOUBLE == dataType) {
					NcData<double[]> ncData = new NcData<double[]>();
					double[] data = null;
					if (sectionSpec != null && !sectionSpec.isEmpty()) {
						data = (double[]) v.read(sectionSpec).reduce().copyTo1DJavaArray();
						if (avgNumList != null && !avgNumList.isEmpty() && avgNumList.size() == dimSize) {
							int avgNum1 = avgNumList.get(0);
							if (avgNum1 > 1 && avgNum1 < data.length) {
								Double cuts = Double.valueOf(data.length) / avgNum1;
								Double deci = cuts - cuts.intValue();
								int newSize = cuts.intValue();
								if (deci != Double.valueOf(0)) {
									newSize++;
								}
								double[] newData = new double[newSize];
								for (int i = 0; i < newSize; i++) {
									double pieceTotal = 0;
									int count = 0;
									for (int a = i * avgNum1; a < ((i + 1) * avgNum1 > data.length ? data.length
											: (i + 1) * avgNum1); a++) {
										if (data[a] < 999999) {
											pieceTotal = pieceTotal + data[a];
										}
										count++;
									}
									newData[i] = pieceTotal / count;
								}
								data = newData;
							}
						}
					} else {
						data = (double[]) v.read().reduce().copyTo1DJavaArray();
					}
					ncData.setData(data);
					return ncData;
				} else if (DataType.LONG == dataType) {
					NcData<long[]> ncData = new NcData<long[]>();
					long[] data = null;
					if (sectionSpec != null && !sectionSpec.isEmpty()) {
						data = (long[]) v.read(sectionSpec).reduce().copyTo1DJavaArray();
						if (avgNumList != null && !avgNumList.isEmpty() && avgNumList.size() == dimSize) {
							int avgNum1 = avgNumList.get(0);
							if (avgNum1 > 1 && avgNum1 < data.length) {
								Double cuts = Double.valueOf(data.length) / avgNum1;
								Double deci = cuts - cuts.intValue();
								int newSize = cuts.intValue();
								if (deci != Double.valueOf(0)) {
									newSize++;
								}
								long[] newData = new long[newSize];
								for (int i = 0; i < newSize; i++) {
									long pieceTotal = 0;
									int count = 0;
									for (int a = i * avgNum1; a < ((i + 1) * avgNum1 > data.length ? data.length
											: (i + 1) * avgNum1); a++) {
										if (data[a] < 999999) {
											pieceTotal = pieceTotal + data[a];
										}
										count++;
									}
									newData[i] = pieceTotal / count;
								}
								data = newData;
							}
						}
					} else {
						data = (long[]) v.read().reduce().copyTo1DJavaArray();
					}
					ncData.setData(data);
					return ncData;
				}
			} else if (dimSize == 2) {
				if (DataType.FLOAT == dataType) {
					NcData<float[][]> ncData = new NcData<float[][]>();
					float[][] data = null;
					if (sectionSpec != null && !sectionSpec.isEmpty()) {
						data = (float[][]) v.read(sectionSpec).reduce().copyToNDJavaArray();
						if (avgNumList != null && !avgNumList.isEmpty() && avgNumList.size() == dimSize) {
							int avgNum1 = avgNumList.get(0);
							int avgNum2 = avgNumList.get(1);
							if ((avgNum1 > 1 || avgNum2 > 1) && (avgNum1 < data.length && avgNum2 < data[0].length)) {
								System.out.println(data.length);
								System.out.println(data[0].length);
								Double cuts1 = Double.valueOf(data.length) / avgNum1;
								Double cuts2 = Double.valueOf(data[0].length) / avgNum2;
								Double deci1 = cuts1 - cuts1.intValue();
								Double deci2 = cuts2 - cuts2.intValue();
								int newSize1 = cuts1.intValue();
								int newSize2 = cuts2.intValue();
								if (deci1 != Double.valueOf(0)) {
									newSize1++;
								}
								if (deci2 != Double.valueOf(0)) {
									newSize2++;
								}
								float[][] newData = new float[newSize1][newSize2];
								for (int i = 0; i < newSize1; i++) {
									for (int j = 0; j < newSize2; j++) {
										float pieceTotal = 0;
										int count = 0;
										for (int a = i * avgNum1; a < ((i + 1) * avgNum1 > data.length ? data.length
												: (i + 1) * avgNum1); a++) {
											for (int b = j * avgNum2; b < ((j + 1) * avgNum2 > data[a].length ? data[a].length
													: (j + 1) * avgNum2); b++) {
												if(b == 301) {
													System.out.println("301");
												}
												if (data[a][b] < 999999) {
													pieceTotal = pieceTotal + data[a][b];
													count++;
												}
											}
										}
										newData[i][j] = pieceTotal / count;
									}
								}
								data = newData;
							}
						}
					} else {
						data = (float[][]) v.read().reduce().copyToNDJavaArray();
					}
					ncData.setData(data);
					return ncData;
				} else if (DataType.DOUBLE == dataType) {
					NcData<double[][]> ncData = new NcData<double[][]>();
					double[][] data = null;
					if (sectionSpec != null && !sectionSpec.isEmpty()) {
						data = (double[][]) v.read(sectionSpec).reduce().copyToNDJavaArray();
						if (avgNumList != null && !avgNumList.isEmpty() && avgNumList.size() == dimSize) {
							int avgNum1 = avgNumList.get(0);
							int avgNum2 = avgNumList.get(1);
							if ((avgNum1 > 1 || avgNum2 > 1) && (avgNum1 < data.length && avgNum2 < data[0].length)) {
								Double cuts1 = Double.valueOf(data.length) / avgNum1;
								Double cuts2 = Double.valueOf(data[0].length) / avgNum2;
								Double deci1 = cuts1 - cuts1.intValue();
								Double deci2 = cuts2 - cuts2.intValue();
								int newSize1 = cuts1.intValue();
								int newSize2 = cuts2.intValue();
								if (deci1 != Double.valueOf(0)) {
									newSize1++;
								}
								if (deci2 != Double.valueOf(0)) {
									newSize2++;
								}
								double[][] newData = new double[newSize1][newSize2];
								for (int i = 0; i < newSize1; i++) {
									for (int j = 0; j < newSize2; j++) {
										double pieceTotal = 0;
										int count = 0;
										for (int a = i * avgNum1; a < ((i + 1) * avgNum1 > data.length ? data.length
												: (i + 1) * avgNum1); a++) {
											for (int b = j * avgNum2; b < ((j + 1) * avgNum2 > data[a].length
													? data[a].length
													: (j + 1) * avgNum2); b++) {
												if (data[a][b] < 999999) {
													pieceTotal = pieceTotal + data[a][b];
													count++;
												}
											}
										}
										newData[i][j] = pieceTotal / count;
									}
								}
								data = newData;
							}
						}
					} else {
						data = (double[][]) v.read().reduce().copyToNDJavaArray();
					}
					ncData.setData(data);
					return ncData;
				} else if (DataType.LONG == dataType) {
					NcData<long[][]> ncData = new NcData<long[][]>();
					long[][] data = null;
					if (sectionSpec != null && !sectionSpec.isEmpty()) {
						data = (long[][]) v.read(sectionSpec).reduce().copyToNDJavaArray();
						if (avgNumList != null && !avgNumList.isEmpty() && avgNumList.size() == dimSize) {
							int avgNum1 = avgNumList.get(0);
							int avgNum2 = avgNumList.get(1);
							if ((avgNum1 > 1 || avgNum2 > 1) && (avgNum1 < data.length && avgNum2 < data[0].length)) {
								Double cuts1 = Double.valueOf(data.length) / avgNum1;
								Double cuts2 = Double.valueOf(data[0].length) / avgNum2;
								Double deci1 = cuts1 - cuts1.intValue();
								Double deci2 = cuts2 - cuts2.intValue();
								int newSize1 = cuts1.intValue();
								int newSize2 = cuts2.intValue();
								if (deci1 != Double.valueOf(0)) {
									newSize1++;
								}
								if (deci2 != Double.valueOf(0)) {
									newSize2++;
								}
								long[][] newData = new long[newSize1][newSize2];
								for (int i = 0; i < newSize1; i++) {
									for (int j = 0; j < newSize2; j++) {
										long pieceTotal = 0;
										int count = 0;
										for (int a = i * avgNum1; a < ((i + 1) * avgNum1 > data.length ? data.length
												: (i + 1) * avgNum1); a++) {
											for (int b = j * avgNum2; b < ((j + 1) * avgNum2 > data[a].length
													? data[a].length
													: (j + 1) * avgNum2); b++) {
												if (data[a][b] < 999999) {
													pieceTotal = pieceTotal + data[a][b];
													count++;
												}
											}
										}
										newData[i][j] = pieceTotal / count;
									}
								}
								data = newData;
							}
						}
					} else {
						data = (long[][]) v.read().reduce().copyToNDJavaArray();
					}
					ncData.setData(data);
					return ncData;
				}
			} else if (dimSize == 3) {
				if (DataType.FLOAT == dataType) {
					NcData<float[][][]> ncData = new NcData<float[][][]>();
					float[][][] data = null;
					if (sectionSpec != null && !sectionSpec.isEmpty()) {
						data = (float[][][]) v.read(sectionSpec).reduce().copyToNDJavaArray();
						if (avgNumList != null && !avgNumList.isEmpty() && avgNumList.size() == dimSize) {
							int avgNum1 = avgNumList.get(0);
							int avgNum2 = avgNumList.get(1);
							int avgNum3 = avgNumList.get(2);
							if ((avgNum1 > 1 || avgNum2 > 1 || avgNum3 > 1) && (avgNum1 < data.length
									&& avgNum2 < data[0].length && avgNum3 < data[0][0].length)) {
								Double cuts1 = Double.valueOf(data.length) / avgNum1;
								Double cuts2 = Double.valueOf(data[0].length) / avgNum2;
								Double cuts3 = Double.valueOf(data[0][0].length) / avgNum3;
								Double deci1 = cuts1 - cuts1.intValue();
								Double deci2 = cuts2 - cuts2.intValue();
								Double deci3 = cuts3 - cuts3.intValue();
								int newSize1 = cuts1.intValue();
								int newSize2 = cuts2.intValue();
								int newSize3 = cuts3.intValue();
								if (deci1 != Double.valueOf(0)) {
									newSize1++;
								}
								if (deci2 != Double.valueOf(0)) {
									newSize2++;
								}
								if (deci3 != Double.valueOf(0)) {
									newSize3++;
								}
								float[][][] newData = new float[newSize1][newSize2][newSize3];
								for (int i = 0; i < newSize1; i++) {
									for (int j = 0; j < newSize2; j++) {
										for (int k = 0; k < newSize3; k++) {
											float pieceTotal = 0;
											int count = 0;
											for (int a = i * avgNum1; a < ((i + 1) * avgNum1 > data.length ? data.length
													: (i + 1) * avgNum1); a++) {
												for (int b = j * avgNum2; b < ((j + 1) * avgNum2 > data[a].length
														? data[a].length
														: (j + 1) * avgNum2); b++) {
													for (int c = k * avgNum3; c < ((k + 1) * avgNum3 > data[a][b].length
															? data[a][b].length
															: (k + 1) * avgNum3); c++) {
														if (data[a][b][c] < 999999) {
															pieceTotal = pieceTotal + data[a][b][c];
														}
														count++;
													}
												}
											}
											newData[i][j][k] = pieceTotal / count;
										}
									}
								}
								data = newData;
							}
						}
					} else {
						data = (float[][][]) v.read().reduce().copyToNDJavaArray();
					}
					ncData.setData(data);
					return ncData;
				} else if (DataType.DOUBLE == dataType) {
					NcData<double[][][]> ncData = new NcData<double[][][]>();
					double[][][] data = null;
					if (sectionSpec != null && !sectionSpec.isEmpty()) {
						data = (double[][][]) v.read(sectionSpec).reduce().copyToNDJavaArray();
						if (avgNumList != null && !avgNumList.isEmpty() && avgNumList.size() == dimSize) {
							int avgNum1 = avgNumList.get(0);
							int avgNum2 = avgNumList.get(1);
							int avgNum3 = avgNumList.get(2);
							if ((avgNum1 > 1 || avgNum2 > 1 || avgNum3 > 1) && (avgNum1 < data.length
									&& avgNum2 < data[0].length && avgNum3 < data[0][0].length)) {
								Double cuts1 = Double.valueOf(data.length) / avgNum1;
								Double cuts2 = Double.valueOf(data[0].length) / avgNum2;
								Double cuts3 = Double.valueOf(data[0][0].length) / avgNum3;
								Double deci1 = cuts1 - cuts1.intValue();
								Double deci2 = cuts2 - cuts2.intValue();
								Double deci3 = cuts3 - cuts3.intValue();
								int newSize1 = cuts1.intValue();
								int newSize2 = cuts2.intValue();
								int newSize3 = cuts3.intValue();
								if (deci1 != Double.valueOf(0)) {
									newSize1++;
								}
								if (deci2 != Double.valueOf(0)) {
									newSize2++;
								}
								if (deci3 != Double.valueOf(0)) {
									newSize3++;
								}
								double[][][] newData = new double[newSize1][newSize2][newSize3];
								for (int i = 0; i < newSize1; i++) {
									for (int j = 0; j < newSize2; j++) {
										for (int k = 0; k < newSize3; k++) {
											double pieceTotal = 0;
											int count = 0;
											for (int a = i * avgNum1; a < ((i + 1) * avgNum1 > data.length ? data.length
													: (i + 1) * avgNum1); a++) {
												for (int b = j * avgNum2; b < ((j + 1) * avgNum2 > data[a].length
														? data[a].length
														: (j + 1) * avgNum2); b++) {
													for (int c = k * avgNum3; c < ((k + 1) * avgNum3 > data[a][b].length
															? data[a][b].length
															: (k + 1) * avgNum3); c++) {
														if (data[a][b][c] < 999999) {
															pieceTotal = pieceTotal + data[a][b][c];
														}
														count++;
													}
												}
											}
											newData[i][j][k] = pieceTotal / count;
										}
									}
								}
								data = newData;
							}
						}
					} else {
						data = (double[][][]) v.read().reduce().copyToNDJavaArray();
					}
					ncData.setData(data);
					return ncData;
				} else if (DataType.LONG == dataType) {
					NcData<long[][][]> ncData = new NcData<long[][][]>();
					long[][][] data = null;
					if (sectionSpec != null && !sectionSpec.isEmpty()) {
						data = (long[][][]) v.read(sectionSpec).reduce().copyToNDJavaArray();
						if (avgNumList != null && !avgNumList.isEmpty() && avgNumList.size() == dimSize) {
							int avgNum1 = avgNumList.get(0);
							int avgNum2 = avgNumList.get(1);
							int avgNum3 = avgNumList.get(2);
							if ((avgNum1 > 1 || avgNum2 > 1 || avgNum3 > 1) && (avgNum1 < data.length
									&& avgNum2 < data[0].length && avgNum3 < data[0][0].length)) {
								Double cuts1 = Double.valueOf(data.length) / avgNum1;
								Double cuts2 = Double.valueOf(data[0].length) / avgNum2;
								Double cuts3 = Double.valueOf(data[0][0].length) / avgNum3;
								Double deci1 = cuts1 - cuts1.intValue();
								Double deci2 = cuts2 - cuts2.intValue();
								Double deci3 = cuts3 - cuts3.intValue();
								int newSize1 = cuts1.intValue();
								int newSize2 = cuts2.intValue();
								int newSize3 = cuts3.intValue();
								if (deci1 != Double.valueOf(0)) {
									newSize1++;
								}
								if (deci2 != Double.valueOf(0)) {
									newSize2++;
								}
								if (deci3 != Double.valueOf(0)) {
									newSize3++;
								}
								long[][][] newData = new long[newSize1][newSize2][newSize3];
								for (int i = 0; i < newSize1; i++) {
									for (int j = 0; j < newSize2; j++) {
										for (int k = 0; k < newSize3; k++) {
											long pieceTotal = 0;
											int count = 0;
											for (int a = i * avgNum1; a < ((i + 1) * avgNum1 > data.length ? data.length
													: (i + 1) * avgNum1); a++) {
												for (int b = j * avgNum2; b < ((j + 1) * avgNum2 > data[a].length
														? data[a].length
														: (j + 1) * avgNum2); b++) {
													for (int c = k * avgNum3; c < ((k + 1) * avgNum3 > data[a][b].length
															? data[a][b].length
															: (k + 1) * avgNum3); c++) {
														if (data[a][b][c] < 999999) {
															pieceTotal = pieceTotal + data[a][b][c];
														}
														count++;
													}
												}
											}
											newData[i][j][k] = pieceTotal / count;
										}
									}
								}
								data = newData;
							}
						}
					} else {
						data = (long[][][]) v.read().reduce().copyToNDJavaArray();
					}
					ncData.setData(data);
					return ncData;
				}
			} else if (dimSize == 4) {
				if (DataType.FLOAT == dataType) {
					NcData<float[][][][]> ncData = new NcData<float[][][][]>();
					float[][][][] data = null;
					if (sectionSpec != null && !sectionSpec.isEmpty()) {
						data = (float[][][][]) v.read(sectionSpec).reduce().copyToNDJavaArray();
						if (avgNumList != null && !avgNumList.isEmpty() && avgNumList.size() == dimSize) {
							int avgNum1 = avgNumList.get(0);
							int avgNum2 = avgNumList.get(1);
							int avgNum3 = avgNumList.get(2);
							int avgNum4 = avgNumList.get(3);
							if ((avgNum1 > 1 || avgNum2 > 1 || avgNum3 > 1 || avgNum4 > 1)
									&& (avgNum1 < data.length && avgNum2 < data[0].length && avgNum3 < data[0][0].length
											&& avgNum4 < data[0][0][0].length)) {
								Double cuts1 = Double.valueOf(data.length) / avgNum1;
								Double cuts2 = Double.valueOf(data[0].length) / avgNum2;
								Double cuts3 = Double.valueOf(data[0][0].length) / avgNum3;
								Double cuts4 = Double.valueOf(data[0][0][0].length) / avgNum4;
								Double deci1 = cuts1 - cuts1.intValue();
								Double deci2 = cuts2 - cuts2.intValue();
								Double deci3 = cuts3 - cuts3.intValue();
								Double deci4 = cuts4 - cuts4.intValue();
								int newSize1 = cuts1.intValue();
								int newSize2 = cuts2.intValue();
								int newSize3 = cuts3.intValue();
								int newSize4 = cuts4.intValue();
								if (deci1 != Double.valueOf(0)) {
									newSize1++;
								}
								if (deci2 != Double.valueOf(0)) {
									newSize2++;
								}
								if (deci3 != Double.valueOf(0)) {
									newSize3++;
								}
								if (deci4 != Double.valueOf(0)) {
									newSize4++;
								}

								float[][][][] newData = new float[newSize1][newSize2][newSize3][newSize4];
								for (int i = 0; i < newSize1; i++) {
									for (int j = 0; j < newSize2; j++) {
										for (int k = 0; k < newSize3; k++) {
											for (int l = 0; l < newSize4; l++) {
												float pieceTotal = 0;
												int count = 0;
												for (int a = i * avgNum1; a < ((i + 1) * avgNum1 > data.length
														? data.length
														: (i + 1) * avgNum1); a++) {
													for (int b = j * avgNum2; b < ((j + 1) * avgNum2 > data[a].length
															? data[a].length
															: (j + 1) * avgNum2); b++) {
														for (int c = k
																* avgNum3; c < ((k + 1) * avgNum3 > data[a][b].length
																		? data[a][b].length
																		: (k + 1) * avgNum3); c++) {
															for (int d = l * avgNum4; d < ((l + 1)
																	* avgNum4 > data[a][b][c].length
																			? data[a][b][c].length
																			: (l + 1) * avgNum4); d++) {
																if (data[a][b][c][d] < 999999) {
																	pieceTotal = pieceTotal + data[a][b][c][d];
																}
																count++;
															}
														}
													}
												}
												newData[i][j][k][l] = pieceTotal / count;
											}
										}
									}
								}
								data = newData;
							}
						}
					} else {
						data = (float[][][][]) v.read().reduce().copyToNDJavaArray();
					}
					ncData.setData(data);
					return ncData;
				} else if (DataType.DOUBLE == dataType) {
					NcData<double[][][][]> ncData = new NcData<double[][][][]>();
					double[][][][] data = null;
					if (sectionSpec != null && !sectionSpec.isEmpty()) {
						data = (double[][][][]) v.read(sectionSpec).reduce().copyToNDJavaArray();
						if (avgNumList != null && !avgNumList.isEmpty() && avgNumList.size() == dimSize) {
							int avgNum1 = avgNumList.get(0);
							int avgNum2 = avgNumList.get(1);
							int avgNum3 = avgNumList.get(2);
							int avgNum4 = avgNumList.get(3);
							if ((avgNum1 > 1 || avgNum2 > 1 || avgNum3 > 1 || avgNum4 > 1)
									&& (avgNum1 < data.length && avgNum2 < data[0].length && avgNum3 < data[0][0].length
											&& avgNum4 < data[0][0][0].length)) {
								Double cuts1 = Double.valueOf(data.length) / avgNum1;
								Double cuts2 = Double.valueOf(data[0].length) / avgNum2;
								Double cuts3 = Double.valueOf(data[0][0].length) / avgNum3;
								Double cuts4 = Double.valueOf(data[0][0][0].length) / avgNum4;
								Double deci1 = cuts1 - cuts1.intValue();
								Double deci2 = cuts2 - cuts2.intValue();
								Double deci3 = cuts3 - cuts3.intValue();
								Double deci4 = cuts4 - cuts4.intValue();
								int newSize1 = cuts1.intValue();
								int newSize2 = cuts2.intValue();
								int newSize3 = cuts3.intValue();
								int newSize4 = cuts4.intValue();
								if (deci1 != Double.valueOf(0)) {
									newSize1++;
								}
								if (deci2 != Double.valueOf(0)) {
									newSize2++;
								}
								if (deci3 != Double.valueOf(0)) {
									newSize3++;
								}
								if (deci4 != Double.valueOf(0)) {
									newSize4++;
								}

								double[][][][] newData = new double[newSize1][newSize2][newSize3][newSize4];
								for (int i = 0; i < newSize1; i++) {
									for (int j = 0; j < newSize2; j++) {
										for (int k = 0; k < newSize3; k++) {
											for (int l = 0; l < newSize4; l++) {
												double pieceTotal = 0;
												int count = 0;
												for (int a = i * avgNum1; a < ((i + 1) * avgNum1 > data.length
														? data.length
														: (i + 1) * avgNum1); a++) {
													for (int b = j * avgNum2; b < ((j + 1) * avgNum2 > data[a].length
															? data[a].length
															: (j + 1) * avgNum2); b++) {
														for (int c = k
																* avgNum3; c < ((k + 1) * avgNum3 > data[a][b].length
																		? data[a][b].length
																		: (k + 1) * avgNum3); c++) {
															for (int d = l * avgNum4; d < ((l + 1)
																	* avgNum4 > data[a][b][c].length
																			? data[a][b][c].length
																			: (l + 1) * avgNum4); d++) {
																if (data[a][b][c][d] < 999999) {
																	pieceTotal = pieceTotal + data[a][b][c][d];
																}
																count++;
															}
														}
													}
												}
												newData[i][j][k][l] = pieceTotal / count;
											}
										}
									}
								}
								data = newData;
							}
						}
					} else {
						data = (double[][][][]) v.read().reduce().copyToNDJavaArray();
					}
					ncData.setData(data);
					return ncData;
				} else if (DataType.LONG == dataType) {
					NcData<long[][][][]> ncData = new NcData<long[][][][]>();
					long[][][][] data = null;
					if (sectionSpec != null && !sectionSpec.isEmpty()) {
						data = (long[][][][]) v.read(sectionSpec).reduce().copyToNDJavaArray();
						if (avgNumList != null && !avgNumList.isEmpty() && avgNumList.size() == dimSize) {
							int avgNum1 = avgNumList.get(0);
							int avgNum2 = avgNumList.get(1);
							int avgNum3 = avgNumList.get(2);
							int avgNum4 = avgNumList.get(3);
							if ((avgNum1 > 1 || avgNum2 > 1 || avgNum3 > 1 || avgNum4 > 1)
									&& (avgNum1 < data.length && avgNum2 < data[0].length && avgNum3 < data[0][0].length
											&& avgNum4 < data[0][0][0].length)) {
								Double cuts1 = Double.valueOf(data.length) / avgNum1;
								Double cuts2 = Double.valueOf(data[0].length) / avgNum2;
								Double cuts3 = Double.valueOf(data[0][0].length) / avgNum3;
								Double cuts4 = Double.valueOf(data[0][0][0].length) / avgNum4;
								Double deci1 = cuts1 - cuts1.intValue();
								Double deci2 = cuts2 - cuts2.intValue();
								Double deci3 = cuts3 - cuts3.intValue();
								Double deci4 = cuts4 - cuts4.intValue();
								int newSize1 = cuts1.intValue();
								int newSize2 = cuts2.intValue();
								int newSize3 = cuts3.intValue();
								int newSize4 = cuts4.intValue();
								if (deci1 != Double.valueOf(0)) {
									newSize1++;
								}
								if (deci2 != Double.valueOf(0)) {
									newSize2++;
								}
								if (deci3 != Double.valueOf(0)) {
									newSize3++;
								}
								if (deci4 != Double.valueOf(0)) {
									newSize4++;
								}

								long[][][][] newData = new long[newSize1][newSize2][newSize3][newSize4];
								for (int i = 0; i < newSize1; i++) {
									for (int j = 0; j < newSize2; j++) {
										for (int k = 0; k < newSize3; k++) {
											for (int l = 0; l < newSize4; l++) {
												long pieceTotal = 0;
												int count = 0;
												for (int a = i * avgNum1; a < ((i + 1) * avgNum1 > data.length
														? data.length
														: (i + 1) * avgNum1); a++) {
													for (int b = j * avgNum2; b < ((j + 1) * avgNum2 > data[a].length
															? data[a].length
															: (j + 1) * avgNum2); b++) {
														for (int c = k
																* avgNum3; c < ((k + 1) * avgNum3 > data[a][b].length
																		? data[a][b].length
																		: (k + 1) * avgNum3); c++) {
															for (int d = l * avgNum4; d < ((l + 1)
																	* avgNum4 > data[a][b][c].length
																			? data[a][b][c].length
																			: (l + 1) * avgNum4); d++) {
																if (data[a][b][c][d] < 999999) {
																	pieceTotal = pieceTotal + data[a][b][c][d];
																}
																count++;
															}
														}
													}
												}
												newData[i][j][k][l] = pieceTotal / count;
											}
										}
									}
								}
								data = newData;
							}
						}
					} else {
						data = (long[][][][]) v.read().reduce().copyToNDJavaArray();
					}
					ncData.setData(data);
					return ncData;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		List<NcParam> params = new ArrayList<>();
		NcParam ncParam = new NcParam();
		ncParam.setVariableName("ECHOTOP");
		Map<Integer, NcParam1D> v1DParams = new HashMap<>();
		NcParam1D<Float> param1D = new NcParam1D<Float>();
		param1D.setVariable1DName("lon");
		// param1D.setStartValue(102.35f);
		// param1D.setEndValue(103.88f);
		param1D.setStartInt(100);
		param1D.setEndInt(600);
		param1D.setCutNum(5);
		param1D.setAvgNum(5);
		v1DParams.put(2, param1D);
		NcParam1D<Float> param1D2 = new NcParam1D<Float>();
		param1D2.setVariable1DName("lat");
		param1D2.setStartValue(25.68f);
		param1D2.setEndValue(23.55f);
		param1D2.setCutNum(4);
		param1D2.setAvgNum(4);
		v1DParams.put(1, param1D2);
		ncParam.setV1DParams(v1DParams);
		params.add(ncParam);

		String path = "D://home/nriet/product_fz/DATA/MOP/LAPS/20170807/LAPS_ECHOTOP_20170807005000_000.nc";
		List<NcData> dataList = NetcdfFileReader.readNcFile(path, params);
		for (NcData ncData : dataList) {
			System.out.println(ncData.getVariableName());
			Map<String, Object> dataMap = ncData.getAttributeMap();
			for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
				System.out.println(entry.getKey() + ":" + entry.getValue().toString());
			}
			// float[] data = (float[]) ncData.getData();
			// System.out.println(data.length);
			float[][] data = (float[][]) ncData.getData();
			System.out.println(data);
//			for (int i = 0; i < data.length; i++) {
//				for (int j = 0; j < data[i].length; j++) {
//					System.out.print(data[i][j] + " ");
//				}
//				System.out.println();
//			}
		}
	}
}
