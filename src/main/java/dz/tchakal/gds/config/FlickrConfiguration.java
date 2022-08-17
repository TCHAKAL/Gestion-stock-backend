package dz.tchakal.gds.config;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.RequestContext;
import com.flickr4java.flickr.auth.Auth;
import com.github.scribejava.apis.FlickrApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuth1RequestToken;
import com.github.scribejava.core.oauth.OAuth10aService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

@Configuration
public class FlickrConfiguration {

    @Value("${flickr.api.key}")
    private String apiKey;

    @Value("${flickr.api.secret}")
    private String apiSecret;

//    @Bean
//    public Flickr getFlickr() throws IOException, ExecutionException, InterruptedException, FlickrException {
//        Flickr flickr = new Flickr(apiKey,apiSecret,new REST());
//        OAuth10aService service = new ServiceBuilder(apiKey)
//                .apiSecret(apiSecret)
//                .build(FlickrApi.instance(FlickrApi.FlickrPerm.DELETE));
//
//        final Scanner scanner = new Scanner(System.in);
//        final OAuth1RequestToken request = service.getRequestToken();
//        final String authUrl  = service.getAuthorizationUrl(request);
//        System.out.println(authUrl);
//        System.out.println("Paste it here -> ");
//
//        final String authVerifier = scanner.nextLine();
//        OAuth1AccessToken accesToken = service.getAccessToken(request,authVerifier);
//        System.out.println(accesToken.getToken());
//        System.out.println(accesToken.getTokenSecret());
//        Auth auth = flickr.getAuthInterface().checkToken(accesToken);
//        System.out.println("--------------------------------------------");
//        System.out.println(auth.getToken());
//        System.out.println(auth.getTokenSecret());
//        return flickr;
//    }

    @Value("${flickr.api.appKey}")
    private String appKey;

    @Value("${flickr.api.appSecret}")
    private String appSecret;

    @Bean
    public Flickr getFlickr() throws IOException, ExecutionException, InterruptedException, FlickrException {
        Flickr flickr = new Flickr(apiKey,apiSecret,new REST());
        Auth auth = new Auth();
        auth.setToken(appKey);
        auth.setTokenSecret(appSecret);
        RequestContext requestContext =  RequestContext.getRequestContext();
        requestContext.setAuth(auth);
        flickr.setAuth(auth);
        return flickr;
    }
}
