package cn.nriet.manage.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.nriet.entity.Book;

@FeignClient(name = "nriet-book-service", fallback = BookService.BookServiceFallback.class)
public interface BookService {

	@RequestMapping(method = RequestMethod.POST, value = "/save")
	public void save(Book book);
	
	@RequestMapping(method = RequestMethod.GET, value = "/findall")
	public List<Book> getBooks();
	
	@Component
    class BookServiceFallback implements BookService {

        private static final Logger LOGGER = LoggerFactory.getLogger(BookServiceFallback.class);

        @Override
        public List<Book> getBooks() {
            LOGGER.info("异常发生，进入fallback方法");
            return null;
        }

		@Override
		public void save(Book book) {
			LOGGER.info("异常发生，进入fallback方法");
		}
    }
}
