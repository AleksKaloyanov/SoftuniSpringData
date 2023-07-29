package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.StarExportDto;
import softuni.exam.models.dto.StarSeedDto;
import softuni.exam.models.entity.Constellation;
import softuni.exam.models.entity.Star;
import softuni.exam.repository.ConstellationRepository;
import softuni.exam.repository.StarRepository;
import softuni.exam.service.StarService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import static softuni.exam.models.Constants.INVALID_FORMAT;
import static softuni.exam.models.Constants.STAR;

// TODO: Implement all methods

@Service
public class StarServiceImpl implements StarService {
    private static final String STAR_FILE_PATH = "src/main/resources/files/json/stars.json";
    private StarRepository starRepository;
    private ConstellationRepository constellationRepository;
    private Gson gson;
    private ModelMapper modelMapper;
    private ValidationUtil validationUtil;

    public StarServiceImpl(StarRepository starRepository,
                           ConstellationRepository constellationRepository,
                           Gson gson, ModelMapper modelMapper,
                           ValidationUtil validationUtil) {
        this.starRepository = starRepository;
        this.constellationRepository = constellationRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.starRepository.count() > 0;
    }

    @Override
    public String readStarsFileContent() throws IOException {
        return Files.readString(Path.of(STAR_FILE_PATH));
    }

    @Override
    public String importStars() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        List<StarSeedDto> stars =
                Arrays.stream(this.gson.fromJson(readStarsFileContent(), StarSeedDto[].class))
                        .collect(Collectors.toList());

        for (StarSeedDto star : stars) {
            stringBuilder.append(System.lineSeparator());

            if (this.starRepository.findFirstByName(star.getName()).isPresent()
                    || !this.validationUtil.isValid(star)) {
                stringBuilder.append(String.format(INVALID_FORMAT, STAR));
                continue;
            }

            Star mappedStar = this.modelMapper.map(star, Star.class);

            Optional<Constellation> constellation =
                    this.constellationRepository.findById(star.getConstellation());

            mappedStar.setConstellation(constellation.get());

            this.starRepository.save(mappedStar);
            stringBuilder.append(String.format(
                    Locale.US, "Successfully imported star %s - %.2f light years",
                    star.getName(),
                    star.getLightYears())
            );
        }

        return stringBuilder.toString().trim();
    }

    @Override
    public String exportStars() {
        StringBuilder stringBuilder = new StringBuilder();

        List<StarExportDto> stars =
                this.starRepository.findAllRedGiantsThatHaveNeverBeenObservedOrderByLightYears();

        for (StarExportDto star : stars) {
            stringBuilder.append(System.lineSeparator());
            stringBuilder.append(star.toString());
        }

        return stringBuilder.toString().trim();
    }
}
