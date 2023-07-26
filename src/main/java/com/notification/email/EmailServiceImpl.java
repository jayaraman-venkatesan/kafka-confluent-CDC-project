package com.notification.email;
import com.notification.model.EmailPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService{

    @Autowired
    private RestTemplate restTemplate;
    @Override
    public ResponseEntity<String> sendEmail(EmailPayload emailPayload) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth("api", "7063a88b6aaf80dcbcad93b56820c79f-c30053db-c5301aa4");

        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("from", emailPayload.fromEmailAddress);
        map.add("to", emailPayload.toEmailAddress);
        map.add("subject", emailPayload.subject);
        map.add("html", emailPayload.body);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        ResponseEntity<String> response = restTemplate.postForEntity("https://api.mailgun.net/v3/sandbox9e1c781f96f048f291e25b3adf22b94f.mailgun.org/messages",request, String.class);
        return response;
    }
}
