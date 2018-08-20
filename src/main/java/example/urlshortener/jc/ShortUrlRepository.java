
package example.urlshortener.jc;

import example.urlshortener.jc.domain.ShortUrl;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

public interface ShortUrlRepository extends MongoRepository<ShortUrl, String> {

	ShortUrl findByShortVersion(@Param("shortVersion") String shortVersion);

}

