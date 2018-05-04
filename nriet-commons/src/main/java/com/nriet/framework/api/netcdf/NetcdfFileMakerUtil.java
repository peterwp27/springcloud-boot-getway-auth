package com.nriet.framework.api.netcdf;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.nriet.framework.api.netcdf.entity.DataForNc;

import ucar.ma2.Array;
import ucar.ma2.ArrayDouble;
import ucar.ma2.ArrayFloat;
import ucar.ma2.ArrayLong;
import ucar.ma2.DataType;
import ucar.ma2.Index;
import ucar.ma2.InvalidRangeException;
import ucar.nc2.Attribute;
import ucar.nc2.Dimension;
import ucar.nc2.NetcdfFile;
import ucar.nc2.NetcdfFileWriter;
import ucar.nc2.NetcdfFileWriter.Version;
import ucar.nc2.Variable;

public class NetcdfFileMakerUtil {

	/**
	 * NC文件生成(通用)
	 * 
	 * @param filePath
	 *            文件路径
	 * @param fileName
	 *            文件名
	 * @param variablesOver1D
	 *            多维变量数据({key:多维变量名,value:一维变量名(依次排序)})
	 * @param variablesAll
	 *            所有变量数据({key:变量名,value:数据及变量属性等})
	 */
	public static void executeNcFileAny(String filePath, String fileName, Map<String, List<String>> variablesOver1D,
			Map<String, DataForNc> variablesAll) {
		File destFile = new File(filePath);
		if (!destFile.exists()) {
			destFile.mkdirs();
		}
		String path = null;
		if (filePath.endsWith("/") || filePath.endsWith("\\")) {
			path = filePath + fileName;
		} else if (filePath.contains("\\")) {
			path = filePath + "\\" + fileName;
		} else {
			path = filePath + "/" + fileName;
		}

		NetcdfFileWriter ncWriter = null;
		try {
			ncWriter = NetcdfFileWriter.createNew(Version.netcdf3, path);
			if (variablesAll == null || variablesAll.isEmpty()) {
				ncWriter.create();
				ncWriter.flush();
				return;
			}
			if (variablesOver1D == null || variablesOver1D.isEmpty()) {
				Map<Variable, Array> datas = new HashMap<>();
				for (Entry<String, DataForNc> variable : variablesAll.entrySet()) {
					DataForNc ncData = variable.getValue();
					if (ncData == null) {
						continue;
					}
					DataType type = ncData.getDataType();
					Object data1D = null;
					List<Integer> sizeList = new ArrayList<>();
					if (type == DataType.FLOAT) {
						float[] tempData = (float[]) ncData.getVals();
						data1D = tempData;
						sizeList.add(tempData.length);
					} else if (type == DataType.DOUBLE) {
						double[] tempData = (double[]) ncData.getVals();
						data1D = tempData;
						sizeList.add(tempData.length);
					} else if (type == DataType.LONG) {
						long[] tempData = (long[]) ncData.getVals();
						data1D = tempData;
						sizeList.add(tempData.length);
					}
					if (data1D == null || sizeList.isEmpty()) {
						continue;
					}
					ncWriter.addDimension(null, variable.getKey() + "dim", sizeList.get(0));
					Variable v = ncWriter.addVariable(null, variable.getKey(), ncData.getDataType(),
							variable.getKey() + "dim");
					if (ncData.getAttributes() != null) {
						for (Entry<String, String> attribute : ncData.getAttributes().entrySet()) {
							ncWriter.addVariableAttribute(v, new Attribute(attribute.getKey(), attribute.getValue()));
						}
					}
					if (ncData.getFillValue() != null) {
						ncWriter.addVariableAttribute(v, new Attribute("_FillValue", ncData.getFillValue()));
					}
					ncWriter.addVariableAttribute(v, new Attribute("_ChunkSizes", sizeList.get(0)));

					Array array = getArray(sizeList, ncData);
					datas.put(v, array);
				}
				ncWriter.create();
				for (Entry<Variable, Array> writeData : datas.entrySet()) {
					ncWriter.write(writeData.getKey(), writeData.getValue());
				}
			} else {
				Map<Variable, Array> datas = new HashMap<>();
				Map<String, Dimension> dimsMap = new HashMap<>();
				for (Entry<String, DataForNc> data : variablesAll.entrySet()) {
					if (variablesOver1D.containsKey(data.getKey())) {
						continue;
					}
					DataForNc ncData = data.getValue();
					if (ncData == null) {
						continue;
					}
					DataType type = ncData.getDataType();
					Object data1D = null;
					List<Integer> sizeList = new ArrayList<>();
					if (type == DataType.FLOAT) {
						float[] tempData = (float[]) ncData.getVals();
						data1D = tempData;
						sizeList.add(tempData.length);
					} else if (type == DataType.DOUBLE) {
						double[] tempData = (double[]) ncData.getVals();
						data1D = tempData;
						sizeList.add(tempData.length);
					} else if (type == DataType.LONG) {
						long[] tempData = (long[]) ncData.getVals();
						data1D = tempData;
						sizeList.add(tempData.length);
					} else if (type == DataType.CHAR) {
						char[] tempData = (char[]) ncData.getVals();
						data1D = tempData;
						sizeList.add(tempData.length);
					}
					if (data1D == null || sizeList.isEmpty()) {
						continue;
					}
					Dimension dim = ncWriter.addDimension(null, data.getKey(), sizeList.get(0));
					dimsMap.put(data.getKey(), dim);
					Variable v = ncWriter.addVariable(null, data.getKey(), ncData.getDataType(), data.getKey());
					if (ncData.getAttributes() != null) {
						for (Entry<String, String> attribute : ncData.getAttributes().entrySet()) {
							ncWriter.addVariableAttribute(v, new Attribute(attribute.getKey(), attribute.getValue()));
						}
					}
					if (ncData.getFillValue() != null) {
						ncWriter.addVariableAttribute(v, new Attribute("_FillValue", ncData.getFillValue()));
					}
					ncWriter.addVariableAttribute(v, new Attribute("_ChunkSizes", sizeList.get(0)));
					Array array = getArray(sizeList, ncData);
					datas.put(v, array);
				}

				for (Entry<String, List<String>> variable : variablesOver1D.entrySet()) {
					DataForNc ncData = variablesAll.get(variable.getKey());
					if (ncData == null) {
						continue;
					}
					List<Dimension> dims = new ArrayList<>();
					List<Integer> sizeList = new ArrayList<>();
					for (String dimName : variable.getValue()) {
						Dimension dim = dimsMap.get(dimName);
						dims.add(dim);
						sizeList.add(dim.getLength());
					}
					Variable v = ncWriter.addVariable(null, variable.getKey(), ncData.getDataType(), dims);
					if (ncData.getAttributes() != null) {
						for (Entry<String, String> attribute : ncData.getAttributes().entrySet()) {
							ncWriter.addVariableAttribute(v, new Attribute(attribute.getKey(), attribute.getValue()));
						}
					}
					if (ncData.getFillValue() != null) {
						ncWriter.addVariableAttribute(v, new Attribute("_FillValue", ncData.getFillValue()));
					}
					ncWriter.addVariableAttribute(v, new Attribute("_ChunkSizes", sizeList));
					Array array = getArray(sizeList, variablesAll.get(variable.getKey()));
					if (array == null) {
						continue;
					}
					datas.put(v, array);
				}
				ncWriter.create();
				for (Entry<Variable, Array> writeData : datas.entrySet()) {
					ncWriter.write(writeData.getKey(), writeData.getValue());
				}
			}
			ncWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidRangeException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ncWriter != null) {
					ncWriter.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static Array getArray(List<Integer> sizeList, DataForNc data) {
		if (data == null) {
			return null;
		}
		Array array = null;
		DataType type = data.getDataType();
		switch (sizeList.size()) {
		case 1:
			if (type == DataType.FLOAT) {
				array = new ArrayFloat.D1(sizeList.get(0));
				float[] data1D = (float[]) data.getVals();
				for (int i = 0; i < sizeList.get(0); i++) {
					array.setFloat(i, data1D[i]);
				}
			} else if (type == DataType.DOUBLE) {
				array = new ArrayDouble.D1(sizeList.get(0));
				double[] data1D = (double[]) data.getVals();
				for (int i = 0; i < sizeList.get(0); i++) {
					array.setDouble(i, data1D[i]);
				}
			} else if (type == DataType.LONG) {
				array = new ArrayLong.D1(sizeList.get(0));
				long[] data1D = (long[]) data.getVals();
				for (int i = 0; i < sizeList.get(0); i++) {
					array.setLong(i, data1D[i]);
				}
			}
			break;
		case 2:
			if (type == DataType.FLOAT) {
				array = new ArrayFloat.D2(sizeList.get(0), sizeList.get(1));
				Index index2 = array.getIndex();
				float[][] data2D = (float[][]) data.getVals();
				for (int i = 0; i < sizeList.get(0); i++) {
					for (int j = 0; j < sizeList.get(1); j++) {
						array.setFloat(index2.set(i, j), data2D[i][j]);
					}
				}
			} else if (type == DataType.DOUBLE) {
				array = new ArrayDouble.D2(sizeList.get(0), sizeList.get(1));
				Index index2 = array.getIndex();
				double[][] data2D = (double[][]) data.getVals();
				for (int i = 0; i < sizeList.get(0); i++) {
					for (int j = 0; j < sizeList.get(1); j++) {
						array.setDouble(index2.set(i, j), data2D[i][j]);
					}
				}
			} else if (type == DataType.LONG) {
				array = new ArrayLong.D2(sizeList.get(0), sizeList.get(1));
				Index index2 = array.getIndex();
				long[][] data2D = (long[][]) data.getVals();
				for (int i = 0; i < sizeList.get(0); i++) {
					for (int j = 0; j < sizeList.get(1); j++) {
						array.setLong(index2.set(i, j), data2D[i][j]);
					}
				}
			}
			break;
		case 3:
			if (type == DataType.FLOAT) {
				array = new ArrayFloat.D3(sizeList.get(0), sizeList.get(1), sizeList.get(2));
				Index index3 = array.getIndex();
				float[][][] data3D = (float[][][]) data.getVals();
				for (int i = 0; i < sizeList.get(0); i++) {
					for (int j = 0; j < sizeList.get(1); j++) {
						for (int k = 0; k < sizeList.get(2); k++) {
							array.setFloat(index3.set(i, j, k), data3D[i][j][k]);
						}
					}
				}
			} else if (type == DataType.DOUBLE) {
				array = new ArrayDouble.D3(sizeList.get(0), sizeList.get(1), sizeList.get(2));
				Index index3 = array.getIndex();
				double[][][] data3D = (double[][][]) data.getVals();
				for (int i = 0; i < sizeList.get(0); i++) {
					for (int j = 0; j < sizeList.get(1); j++) {
						for (int k = 0; k < sizeList.get(2); k++) {
							array.setDouble(index3.set(i, j, k), data3D[i][j][k]);
						}
					}
				}
			} else if (type == DataType.LONG) {
				array = new ArrayLong.D3(sizeList.get(0), sizeList.get(1), sizeList.get(2));
				Index index3 = array.getIndex();
				long[][][] data3D = (long[][][]) data.getVals();
				for (int i = 0; i < sizeList.get(0); i++) {
					for (int j = 0; j < sizeList.get(1); j++) {
						for (int k = 0; k < sizeList.get(2); k++) {
							array.setLong(index3.set(i, j, k), data3D[i][j][k]);
						}
					}
				}
			}
			break;
		case 4:
			if (type == DataType.FLOAT) {
				array = new ArrayFloat.D4(sizeList.get(0), sizeList.get(1), sizeList.get(2), sizeList.get(3));
				Index index4 = array.getIndex();
				float[][][][] data4D = (float[][][][]) data.getVals();
				for (int i = 0; i < sizeList.get(0); i++) {
					for (int j = 0; j < sizeList.get(1); j++) {
						for (int k = 0; k < sizeList.get(2); k++) {
							for (int l = 0; l < sizeList.get(3); l++) {
								array.setFloat(index4.set(i, j, k, l), data4D[i][j][k][l]);
							}
						}
					}
				}
			} else if (type == DataType.DOUBLE) {
				array = new ArrayDouble.D4(sizeList.get(0), sizeList.get(1), sizeList.get(2), sizeList.get(3));
				Index index4 = array.getIndex();
				double[][][][] data4D = (double[][][][]) data.getVals();
				for (int i = 0; i < sizeList.get(0); i++) {
					for (int j = 0; j < sizeList.get(1); j++) {
						for (int k = 0; k < sizeList.get(2); k++) {
							for (int l = 0; l < sizeList.get(3); l++) {
								array.setDouble(index4.set(i, j, k, l), data4D[i][j][k][l]);
							}
						}
					}
				}
			} else if (type == DataType.LONG) {
				array = new ArrayLong.D4(sizeList.get(0), sizeList.get(1), sizeList.get(2), sizeList.get(3));
				Index index4 = array.getIndex();
				long[][][][] data4D = (long[][][][]) data.getVals();
				for (int i = 0; i < sizeList.get(0); i++) {
					for (int j = 0; j < sizeList.get(1); j++) {
						for (int k = 0; k < sizeList.get(2); k++) {
							for (int l = 0; l < sizeList.get(3); l++) {
								array.setLong(index4.set(i, j, k, l), data4D[i][j][k][l]);
							}
						}
					}
				}
			}
			break;
		case 5:
			if (type == DataType.FLOAT) {
				array = new ArrayFloat.D5(sizeList.get(0), sizeList.get(1), sizeList.get(2), sizeList.get(3),
						sizeList.get(4));
				Index index5 = array.getIndex();
				float[][][][][] data5D = (float[][][][][]) data.getVals();
				for (int i = 0; i < sizeList.get(0); i++) {
					for (int j = 0; j < sizeList.get(1); j++) {
						for (int k = 0; k < sizeList.get(2); k++) {
							for (int l = 0; l < sizeList.get(3); l++) {
								for (int m = 0; m < sizeList.get(4); m++) {
									array.setFloat(index5.set(i, j, k, l, m), data5D[i][j][k][l][m]);
								}
							}
						}
					}
				}
			} else if (type == DataType.DOUBLE) {
				array = new ArrayDouble.D5(sizeList.get(0), sizeList.get(1), sizeList.get(2), sizeList.get(3),
						sizeList.get(4));
				Index index5 = array.getIndex();
				double[][][][][] data5D = (double[][][][][]) data.getVals();
				for (int i = 0; i < sizeList.get(0); i++) {
					for (int j = 0; j < sizeList.get(1); j++) {
						for (int k = 0; k < sizeList.get(2); k++) {
							for (int l = 0; l < sizeList.get(3); l++) {
								for (int m = 0; m < sizeList.get(4); m++) {
									array.setDouble(index5.set(i, j, k, l, m), data5D[i][j][k][l][m]);
								}
							}
						}
					}
				}
			} else if (type == DataType.LONG) {
				array = new ArrayLong.D5(sizeList.get(0), sizeList.get(1), sizeList.get(2), sizeList.get(3),
						sizeList.get(4));
				Index index5 = array.getIndex();
				long[][][][][] data5D = (long[][][][][]) data.getVals();
				for (int i = 0; i < sizeList.get(0); i++) {
					for (int j = 0; j < sizeList.get(1); j++) {
						for (int k = 0; k < sizeList.get(2); k++) {
							for (int l = 0; l < sizeList.get(3); l++) {
								for (int m = 0; m < sizeList.get(4); m++) {
									array.setLong(index5.set(i, j, k, l, m), data5D[i][j][k][l][m]);
								}
							}
						}
					}
				}
			}
			break;
		case 6:
			if (type == DataType.FLOAT) {
				array = new ArrayFloat.D6(sizeList.get(0), sizeList.get(1), sizeList.get(2), sizeList.get(3),
						sizeList.get(4), sizeList.get(5));
				Index index6 = array.getIndex();
				float[][][][][][] data6D = (float[][][][][][]) data.getVals();
				for (int i = 0; i < sizeList.get(0); i++) {
					for (int j = 0; j < sizeList.get(1); j++) {
						for (int k = 0; k < sizeList.get(2); k++) {
							for (int l = 0; l < sizeList.get(3); l++) {
								for (int m = 0; m < sizeList.get(4); m++) {
									for (int n = 0; n < sizeList.get(5); n++) {
										array.setFloat(index6.set(i, j, k, l, m, n), data6D[i][j][k][l][m][n]);
									}
								}
							}
						}
					}
				}
			} else if (type == DataType.DOUBLE) {
				array = new ArrayDouble.D6(sizeList.get(0), sizeList.get(1), sizeList.get(2), sizeList.get(3),
						sizeList.get(4), sizeList.get(5));
				Index index6 = array.getIndex();
				double[][][][][][] data6D = (double[][][][][][]) data.getVals();
				for (int i = 0; i < sizeList.get(0); i++) {
					for (int j = 0; j < sizeList.get(1); j++) {
						for (int k = 0; k < sizeList.get(2); k++) {
							for (int l = 0; l < sizeList.get(3); l++) {
								for (int m = 0; m < sizeList.get(4); m++) {
									for (int n = 0; n < sizeList.get(5); n++) {
										array.setDouble(index6.set(i, j, k, l, m, n), data6D[i][j][k][l][m][n]);
									}
								}
							}
						}
					}
				}
			} else if (type == DataType.LONG) {
				array = new ArrayLong.D6(sizeList.get(0), sizeList.get(1), sizeList.get(2), sizeList.get(3),
						sizeList.get(4), sizeList.get(5));
				Index index6 = array.getIndex();
				long[][][][][][] data6D = (long[][][][][][]) data.getVals();
				for (int i = 0; i < sizeList.get(0); i++) {
					for (int j = 0; j < sizeList.get(1); j++) {
						for (int k = 0; k < sizeList.get(2); k++) {
							for (int l = 0; l < sizeList.get(3); l++) {
								for (int m = 0; m < sizeList.get(4); m++) {
									for (int n = 0; n < sizeList.get(5); n++) {
										array.setLong(index6.set(i, j, k, l, m, n), data6D[i][j][k][l][m][n]);
									}
								}
							}
						}
					}
				}
			}
			break;
		case 7:
			if (type == DataType.FLOAT) {
				array = new ArrayFloat.D7(sizeList.get(0), sizeList.get(1), sizeList.get(2), sizeList.get(3),
						sizeList.get(4), sizeList.get(5), sizeList.get(6));
				Index index7 = array.getIndex();
				float[][][][][][][] data7D = (float[][][][][][][]) data.getVals();
				for (int a = 0; a < sizeList.get(0); a++) {
					for (int b = 0; b < sizeList.get(1); b++) {
						for (int c = 0; c < sizeList.get(2); c++) {
							for (int d = 0; d < sizeList.get(3); d++) {
								for (int e = 0; e < sizeList.get(4); e++) {
									for (int f = 0; f < sizeList.get(5); f++) {
										for (int g = 0; g < sizeList.get(6); g++) {
											array.setFloat(index7.set(a, b, c, d, e, f, g),
													data7D[a][b][c][d][e][f][g]);
										}
									}
								}
							}
						}
					}
				}
			} else if (type == DataType.DOUBLE) {
				array = new ArrayDouble.D7(sizeList.get(0), sizeList.get(1), sizeList.get(2), sizeList.get(3),
						sizeList.get(4), sizeList.get(5), sizeList.get(6));
				Index index7 = array.getIndex();
				double[][][][][][][] data7D = (double[][][][][][][]) data.getVals();
				for (int a = 0; a < sizeList.get(0); a++) {
					for (int b = 0; b < sizeList.get(1); b++) {
						for (int c = 0; c < sizeList.get(2); c++) {
							for (int d = 0; d < sizeList.get(3); d++) {
								for (int e = 0; e < sizeList.get(4); e++) {
									for (int f = 0; f < sizeList.get(5); f++) {
										for (int g = 0; g < sizeList.get(6); g++) {
											array.setDouble(index7.set(a, b, c, d, e, f, g),
													data7D[a][b][c][d][e][f][g]);
										}
									}
								}
							}
						}
					}
				}
			} else if (type == DataType.LONG) {
				array = new ArrayLong.D7(sizeList.get(0), sizeList.get(1), sizeList.get(2), sizeList.get(3),
						sizeList.get(4), sizeList.get(5), sizeList.get(6));
				Index index7 = array.getIndex();
				long[][][][][][][] data7D = (long[][][][][][][]) data.getVals();
				for (int a = 0; a < sizeList.get(0); a++) {
					for (int b = 0; b < sizeList.get(1); b++) {
						for (int c = 0; c < sizeList.get(2); c++) {
							for (int d = 0; d < sizeList.get(3); d++) {
								for (int e = 0; e < sizeList.get(4); e++) {
									for (int f = 0; f < sizeList.get(5); f++) {
										for (int g = 0; g < sizeList.get(6); g++) {
											array.setLong(index7.set(a, b, c, d, e, f, g), data7D[a][b][c][d][e][f][g]);
										}
									}
								}
							}
						}
					}
				}
			}
			break;
		default:
			break;
		}
		return array;
	}

	public static void addRadarVariable(String path, List<String> fileNames, String filePath, String type) {
		NetcdfFileWriter file = null;
		NetcdfFile file2 = null;
		try {
			file = NetcdfFileWriter.openExisting(path);
			file.setRedefineMode(true);
			for (String fileName : fileNames) {
				if (!fileName.contains("RADAR")) {
					continue;
				}
				String fullPath = filePath + fileName;
				file2 = NetcdfFile.open(fullPath);
				if (file2 == null) {
					continue;
				}
				List<Variable> variables = file2.getVariables();
				if (variables == null || variables.isEmpty() || variables.size() % 7 != 0) {
					continue;
				}
				int size = variables.size() / 7;
				Map<Variable, Array> writeData = new HashMap<>();
				for (int i = 0; i < size; i++) {
					addVariable(type, file, file2, writeData, i, "bottomHgt");
					addVariable(type, file, file2, writeData, i, "dir");
					addVariable(type, file, file2, writeData, i, "intensity");
					addVariable(type, file, file2, writeData, i, "speed");
					addVariable(type, file, file2, writeData, i, "topHgt");
				}
				file.setRedefineMode(false);
				for (Entry<Variable, Array> data : writeData.entrySet()) {
					file.write(data.getKey(), data.getValue());
				}
			}
			file.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidRangeException e) {
			e.printStackTrace();
		} finally {
			if (file != null) {
				try {
					file.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (file2 != null) {
				try {
					file2.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static void addVariable(String type, NetcdfFileWriter file, NetcdfFile file2,
			Map<Variable, Array> writeData, int i, String variableName) {
		Variable variable = file2.findVariable(variableName + (i + 1));
		if (variable != null) {
			try {
				Array array = variable.read();
				List<Dimension> dims = new ArrayList<>();
				for (Dimension dim : variable.getDimensions()) {
					file.addDimension(null, dim.getShortName(), dim.getLength());
					dims.add(dim);
				}
				Variable v = file.addVariable(null, type + "_" + variableName + (i + 1), variable.getDataType(), dims);
				writeData.put(v, array);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * NC文件生成2D
	 * 
	 * @param filePath
	 *            文件路径
	 * @param fileName
	 *            文件名
	 * @param width
	 *            网格横向长度
	 * @param height
	 *            网格纵向高度
	 * @param lonWest
	 *            网格左下角起始经度
	 * @param latSouth
	 *            网格左下角起始纬度
	 * @param gridInterval
	 *            网格间距
	 * @param dataMap
	 *            key:变量名 value:data[i][j]为网格i和j处对应的数据
	 */
	public static void writeNCFile2D(String filePath, String fileName, double lonWest, double latSouth,
			double xgridInterval, double ygridInterval, int nx, int ny, Map<String, float[][]> datas) {
		File destFile = new File(filePath);
		// 判断目标目录是否存在，不存在创建
		if (!destFile.exists()) {
			destFile.mkdirs();
		}
		// 获取生成nc文件路径
		String path = null;
		if (filePath.endsWith("/") || filePath.endsWith("\\")) {
			path = filePath + fileName;
		} else if (filePath.contains("\\")) {
			path = filePath + "\\" + fileName;
		} else {
			path = filePath + "/" + fileName;
		}

		// 网格点经度集合
		double[] lon = new double[ny];
		// 网格点纬度集合
		double[] lat = new double[nx];
		for (int i = 0; i < ny; i++) {
			lon[i] = lonWest + xgridInterval * i;
		}
		for (int j = 0; j < nx; j++) {
			lat[j] = latSouth + ygridInterval * j;
		}

		NetcdfFileWriter ncWriter = null;
		try {
			ncWriter = NetcdfFileWriter.createNew(Version.netcdf3, path);
			// 创建横向纬度
			Dimension xdim = ncWriter.addDimension(null, "lon", lon.length);
			// 创建纵向纬度
			Dimension ydim = ncWriter.addDimension(null, "lat", lat.length);
			List<Dimension> dims = new ArrayList<>();
			dims.add(ydim);
			dims.add(xdim);

			// 创建变量“lon”作为横坐标,对应横向纬度“lon”
			Variable vx = ncWriter.addVariable(null, "lon", DataType.DOUBLE, "lon");
			// 给变量“lon”添加属性
			ncWriter.addVariableAttribute(vx, new Attribute("long_name", "longitude"));
			ncWriter.addVariableAttribute(vx, new Attribute("units", "Degrees_east"));

			// 创建变量“lat”作为纵坐标,对应纵向纬度“lat”
			Variable vy = ncWriter.addVariable(null, "lat", DataType.DOUBLE, "lat");
			// 给变量“lat”添加属性
			ncWriter.addVariableAttribute(vy, new Attribute("long_name", "latitude"));
			ncWriter.addVariableAttribute(vy, new Attribute("units", "Degrees_north"));

			// 存放x和y方向上的坐标数据
			ArrayDouble xValues = new ArrayDouble.D1(lon.length);
			ArrayDouble yValues = new ArrayDouble.D1(lat.length);
			// 将数据源经纬度信息对应写入相应变量
			for (int i = 0; i < lon.length; i++) {
				xValues.setDouble(i, lon[i]);
			}
			for (int j = 0; j < lat.length; j++) {
				yValues.setDouble(j, lat[j]);
			}

			Map<Variable, ArrayDouble> writeData = new HashMap<>();
			if (datas != null && !datas.isEmpty()) {
				for (Entry<String, float[][]> data : datas.entrySet()) {
					Variable v = ncWriter.addVariable(null, data.getKey(), DataType.DOUBLE, dims);
					ncWriter.addVariableAttribute(v, new Attribute("long_name", data.getKey()));
					ArrayDouble values = new ArrayDouble.D2(lat.length, lon.length);
					Index index = values.getIndex();
					for (int i = 0; i < lat.length; i++) {
						for (int j = 0; j < lon.length; j++) {
							values.setDouble(index.set(i, j), data.getValue()[i][j]);
						}
					}
					writeData.put(v, values);
				}
			}
			ncWriter.create();
			ncWriter.write(vx, xValues);
			ncWriter.write(vy, yValues);
			for (Entry<Variable, ArrayDouble> value : writeData.entrySet()) {
				ncWriter.write(value.getKey(), value.getValue());
			}
			ncWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidRangeException e) {
			e.printStackTrace();
		} finally {
			if (ncWriter != null) {
				try {
					ncWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		String filePath = "D://nc/";
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileName = sdf.format(cal.getTime()) + ".NC";
		Map<String, DataForNc> dataMap = new HashMap<>();
		float westLon = 45.0f;
		float eastLon = 145.0f;
		float southLat = -15.0f;
		float northLat = 60.0f;
		float dx = 0.2f;
		float dy = 0.2f;
		int nx = (int) ((eastLon - westLon) / dx);
		int ny = (int) ((northLat - southLat) / dy);
		float[] lons = new float[nx];
		float[] lats = new float[ny];
		for (int i = 0; i < nx; i++) {
			lons[i] = westLon + i * dx;
		}
		for (int j = 0; j < ny; j++) {
			lats[j] = southLat + j * dy;
		}
		double[] times = new double[10];
		for (int i = 0; i < 10; i++) {
			double hours = cal.getTime().getTime();
			cal.add(Calendar.HOUR_OF_DAY, -3);
			times[i] = hours;
		}
		float[] heights = { 100f, 150f, 200f, 250f, 300f, 400f, 500f, 700f, 850f, 925f, 1000f };
		float[][][] data = new float[times.length][ny][nx];
		for (int i = 0; i < times.length; i++) {
			for (int j = 0; j < ny; j++) {
				for (int k = 0; k < nx; k++) {
					data[i][j][k] = (float) (Math.random() * 100);
				}
			}
		}
		double[][][][] data2 = new double[times.length][heights.length][ny][nx];
		for (int i = 0; i < times.length; i++) {
			for (int j = 0; j < heights.length; j++) {
				for (int k = 0; k < ny; k++) {
					for (int l = 0; l < nx; l++) {
						data2[i][j][k][l] = (Math.random() * 20);
					}
				}
			}
		}
		DataForNc lonsdata = new DataForNc();
		lonsdata.setVals(lons);
		Map<String, String> attributeMap = new HashMap<>();
		attributeMap.put("long_name", "lon");
		lonsdata.setAttributes(attributeMap);
		lonsdata.setDataType(DataType.FLOAT);
		dataMap.put("lon", lonsdata);

		DataForNc latsdata = new DataForNc();
		latsdata.setVals(lats);
		Map<String, String> attributeMap2 = new HashMap<>();
		attributeMap2.put("long_name", "lat");
		latsdata.setAttributes(attributeMap2);
		latsdata.setDataType(DataType.FLOAT);
		dataMap.put("lat", latsdata);

		DataForNc timesdata = new DataForNc();
		timesdata.setVals(times);
		Map<String, String> attributeMap3 = new HashMap<>();
		attributeMap3.put("long_nam", "time");
		attributeMap3.put("units", "milliseconds since 1970-01-01 00:00:0.0");
		attributeMap3.put("timezone", "UTC");
		timesdata.setAttributes(attributeMap3);
		timesdata.setDataType(DataType.DOUBLE);
		dataMap.put("time", timesdata);

		DataForNc highdata = new DataForNc();
		highdata.setVals(heights);
		Map<String, String> attributeMap4 = new HashMap<>();
		attributeMap4.put("long_name", "high");
		highdata.setAttributes(attributeMap4);
		highdata.setDataType(DataType.FLOAT);
		dataMap.put("high", highdata);

		DataForNc tmpdata = new DataForNc();
		tmpdata.setVals(data);
		Map<String, String> attributeMap5 = new HashMap<>();
		attributeMap5.put("long_name", "TMP");
		attributeMap5.put("units", "℃");
		tmpdata.setAttributes(attributeMap5);
		tmpdata.setDataType(DataType.FLOAT);
		dataMap.put("TMP", tmpdata);

		DataForNc rhdata = new DataForNc();
		rhdata.setVals(data2);
		Map<String, String> attributeMap6 = new HashMap<>();
		attributeMap6.put("long_name", "RH");
		attributeMap6.put("units", "%");
		rhdata.setAttributes(attributeMap6);
		rhdata.setDataType(DataType.DOUBLE);
		dataMap.put("RH", rhdata);
		Map<String, List<String>> variablesOver1D = new HashMap<>();
		variablesOver1D.put("TMP", Arrays.asList("time", "lat", "lon"));
		variablesOver1D.put("RH", Arrays.asList("time", "high", "lat", "lon"));
		executeNcFileAny(filePath, fileName, variablesOver1D, dataMap);
		System.out.println("结束");
	}
}
