package com.revevol.trial.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.revevol.trial.exception.TrialRevevolException;
import com.revevol.trial.model.User;
import com.revevol.trial.repository.UserRepository;
import com.revevol.trial.utils.TrialStringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.revevol.trial.utils.TrialStringUtils.hasOnlyLetters;
import static java.util.Arrays.stream;

@Service
@Slf4j
public class TrialRevevolService {

    private UserRepository repository;

    @Autowired
    public TrialRevevolService(UserRepository repository) {
        this.repository = repository;
    }

    public static final Function<User, String> ENTITY_STRING_FUNCTION = s ->
            TrialStringUtils.removeAccent(s.getName().toLowerCase()).replaceAll(" ", "_") + "_" +
                    TrialStringUtils.removeAccent(s.getSurname().toLowerCase()).replaceAll(" ", "_");

    public static final String REVEVOL_IT = "@revevol.it";


    public List<User> upload(MultipartFile file) throws Exception {

        if (file == null || file.isEmpty())
            throw new TrialRevevolException("The param file cannot be null", 500);

        final List<User> byFileRef = repository.findByFileRef(file.getOriginalFilename());
        if (!byFileRef.isEmpty())
            throw new TrialRevevolException("File already uploaded", 500);

        CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()));

        final String originalFilename = file.getOriginalFilename();
        final List<User> users = buildUserList(reader, originalFilename);
        log.info("Created Entity for file: {}", originalFilename);

        repository.saveAll(users);
        log.info("Saved Entity for file: {}", originalFilename);
        return users;

    }

    public List<String> retriveEmail(String fileName) {
        final List<String> collect = repository.findByFileRef(fileName)
                .stream()
                .map(User::getEmail)
                .sorted()
                .collect(Collectors.toList());
        log.info("Email address retrieve for file: {}", fileName);
        return collect;
    }


    List<User> buildUserList(CSVReader reader, String originalFilename) throws IOException, CsvValidationException {
        try {
            String[] nextRecord;
            List<User> list = new ArrayList<>();
            while ((nextRecord = reader.readNext()) != null) {
                final List<String> collect = stream(nextRecord).map(StringUtils::normalizeSpace).collect(Collectors.toList());
                final User user = new User();
                if (collect.size() != 2)
                    throw new TrialRevevolException("Some rows don't contain 2 cells", 500);
                final String name = collect.get(0);
                if (!hasOnlyLetters(name))
                    throw new TrialRevevolException("Some words don't contain only letters", 500);
                final String surname = collect.get(1);
                if (!hasOnlyLetters(surname))
                    throw new TrialRevevolException("Some words don't contain only letters", 500);
                user.setName(name);
                user.setSurname(surname);
                list.add(user);
            }
            log.debug("All rows are valid for file: {}", originalFilename);

            final Map<String, List<User>> map =
                    list.stream().collect(Collectors.groupingBy(ENTITY_STRING_FUNCTION));

            List<User> out = new ArrayList<>();
            map.forEach((key, value) -> {
                for (int i = 0; i < value.size(); i++) {
                    final User user = value.get(i);
                    final String email = buildEmail(key, i).toLowerCase();
                    user.setEmail(email);
                    user.setFileRef(originalFilename);
                    out.add(user);
                }
            });
            return out;
        } catch (TrialRevevolException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new TrialRevevolException("File content is not valid", ex.getCause());
        }
    }

    private String buildEmail(String key, int i) {
        final boolean homonymy = i > 0;
        String suffix = homonymy ? "." + i : "";
        if (homonymy)
            log.info("homonymy number {} for {}", i, key.replace("_", " "));
        return key.replace("_", ".") + suffix + REVEVOL_IT;
    }

}
