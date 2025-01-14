package com.example.springintro.services;

import com.example.springintro.entities.Author;
import com.example.springintro.repositories.AuthorRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final static String AUTHOR_FILE_PATH = "src/main/resources/authors.txt";

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void seedAuthor() throws IOException {
        if (authorRepository.count() > 0) {
            return;
        }

        Files.readAllLines(Path.of(AUTHOR_FILE_PATH))
                .forEach(row -> {
                    String[] names = row.split("\\s+");
                    authorRepository.save(new Author(names[0], names[1]));
                });
    }

    @Override
    public Author getRandomAuthor() {
        return authorRepository.findById(getRandomId()).orElse(null);
    }

    @Override
    public List<String> getAuthorsAndCountOfTheirBooks() {

        return authorRepository.findAllByBooksCountDesc()
                .stream()
                .map(author -> String.format("%s %s %d",
                        author.getFirstName(),
                        author.getLastName(),
                        author.getBooks().size()))
                .collect(Collectors.toList());

    }

    private Long getRandomId() {
        return ThreadLocalRandom.current().nextLong(1, authorRepository.count() + 1);
    }
}
