package com.revevol.trial.service;

import com.revevol.trial.model.User;
import com.revevol.trial.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class TrialRevevolServiceTest {

    UserRepository userRepository = Mockito.mock(UserRepository.class);
    TrialRevevolService underTest = new TrialRevevolService(userRepository);


    @Test
    public void test() throws Exception{
        File initialFile = new File("src/test/resources/csv");
        InputStream targetStream = new FileInputStream(initialFile);
        MultipartFile mp = new MockMultipartFile("csv", targetStream);
        Mockito.when(userRepository.findByFileRef(Mockito.any())).thenReturn(Collections.emptyList());

        final List<User> upload = underTest.upload(mp).stream().sorted().collect(Collectors.toList());


        final User user4 = upload.get(0);
        Assertions.assertEquals("beih.geiu.1@revevol.it", user4.getEmail());

        final User user3 = upload.get(1);
        Assertions.assertEquals("beih.geiu@revevol.it", user3.getEmail());

        final User user = upload.get(2);
        Assertions.assertEquals("foo.bar@revevol.it", user.getEmail());

        final User user1 = upload.get(3);
        Assertions.assertEquals("pub.non.so@revevol.it", user1.getEmail());

    }

}