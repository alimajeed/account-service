package com.kyc.accountservice.service;

import com.kyc.accountservice.config.PropertyConfig;
import com.kyc.accountservice.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
@Slf4j
@AllArgsConstructor
public class ThirdPartyVerificationService {
    private final RestTemplate restTemplate;

    @Async("verifyUserExecutor")
    public CompletableFuture<Boolean> verifyUser(UserDto userDto) {
        log.info("Looking up " + userDto.getName());
        String uri = createUri(userDto);
        log.info("uri : {}", uri);
        List response = restTemplate.getForObject(uri, List.class);
        return CompletableFuture.completedFuture(response.size() == 1);
    }

    private String createUri(UserDto userDto){
        UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl(PropertyConfig.VERIFICATION_URL)
                .queryParamIfPresent("name", Optional.ofNullable(userDto.getName()))
                .queryParamIfPresent("email", Optional.ofNullable(userDto.getEmail()))
                .build();
        return uriComponents.toUriString();
    }

    public boolean getFutureResults(CompletableFuture<Boolean> isVerifiedFuture) {
        boolean isVerified = false;
        try {
             isVerified = isVerifiedFuture.get(PropertyConfig.VERIFICATION_TIMEOUT_SECS, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error("##InterruptedException##", e);
        } catch (ExecutionException e) {
            log.error("##ExecutionException##", e);
        } catch (TimeoutException e) {
            log.error("##TimeoutException##", e);
        }
        return isVerified;
    }
}
