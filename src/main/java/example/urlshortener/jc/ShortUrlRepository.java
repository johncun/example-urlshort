package example.urlshortener.jc;

import example.urlshortener.jc.domain.ShortUrl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShortUrlRepository extends MongoRepository<ShortUrl, String> {

	ShortUrl findByShortVersion(@Param("shortVersion") String shortVersion);

	List<ShortUrl> findTop10ByOrderByCreatedOnDesc();
}

