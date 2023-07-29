package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ConstellationSeedDto;
import softuni.exam.models.entity.Constellation;
import softuni.exam.repository.ConstellationRepository;
import softuni.exam.service.ConstellationService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static softuni.exam.models.Constants.*;

// TODO: Implement all methods

@Service
public class ConstellationServiceImpl implements ConstellationService {
    private static final String CONSTELLATION_FILE_PATH = "src/main/resources/files/json/constellations.json";
    private ConstellationRepository constellationRepository;
    private Gson gson;
    private ModelMapper modelMapper;
    private ValidationUtil validationUtil;

    public ConstellationServiceImpl(ConstellationRepository constellationRepository,
                                    Gson gson, ModelMapper modelMapper,
                                    ValidationUtil validationUtil) {
        this.constellationRepository = constellationRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return
                this.constellationRepository.count() > 0;
    }

    @Override
    public String readConstellationsFromFile() throws IOException {
        return Files.readString(Path.of(CONSTELLATION_FILE_PATH));
    }

    @Override
    public String importConstellations() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        List<ConstellationSeedDto> constellations =
                Arrays.stream(this.gson.fromJson(readConstellationsFromFile(), ConstellationSeedDto[].class))
                        .collect(Collectors.toList());

        for (ConstellationSeedDto constellation : constellations) {
            stringBuilder.append(System.lineSeparator());

            if (this.constellationRepository.findFirstByName(constellation.getName()).isPresent()
                    || !this.validationUtil.isValid(constellation)) {
                stringBuilder.append(String.format(INVALID_FORMAT, CONSTELLATION));
                continue;
            }

            this.constellationRepository.save(this.modelMapper.map(constellation, Constellation.class));

            stringBuilder.append(String.format(Locale.US, SUCCESSFUL_FORMAT,
                    CONSTELLATION,
                    constellation.getName(),
                    constellation.getDescription()));
        }

        return stringBuilder.toString().trim();
    }
}
