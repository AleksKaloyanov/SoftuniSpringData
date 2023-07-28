package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.MechanicImportDto;
import softuni.exam.models.entity.Mechanic;
import softuni.exam.repository.MechanicsRepository;
import softuni.exam.service.MechanicsService;
import softuni.exam.util.impl.ValidationUtilImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static softuni.exam.models.Constants.*;

// TODO: Implement all methods
@Service
public class MechanicsServiceImpl implements MechanicsService {
    private static String MECHANICS_FILE_PATH = "src\\main\\resources\\files\\json\\mechanics.json";
    private MechanicsRepository mechanicsRepository;
    private final Gson gson;
    private final ValidationUtilImpl validationUtil;
    private final ModelMapper modelMapper;

    public MechanicsServiceImpl(MechanicsRepository mechanicsRepository,
                                Gson gson,
                                ValidationUtilImpl validationUtil,
                                ModelMapper modelMapper) {
        this.mechanicsRepository = mechanicsRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return mechanicsRepository.count() > 0;
    }

    @Override
    public String readMechanicsFromFile() throws IOException {
        return Files.readString(Path.of(MECHANICS_FILE_PATH));
    }

    @Override
    public String importMechanics() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        List<MechanicImportDto> mechanics = Arrays.stream(gson.fromJson(readMechanicsFromFile(), MechanicImportDto[].class))
                .collect(Collectors.toList());

        for (MechanicImportDto mechanic : mechanics) {
            stringBuilder.append(System.lineSeparator());

            if (mechanicsRepository.findFirstByEmail(mechanic.getEmail()).isPresent()
                    || mechanicsRepository.findFirstByFirstName(mechanic.getFirstName()).isPresent()
                    || !validationUtil.isValid(mechanic)) {
                stringBuilder.append(String.format(INVALID_FORMAT, MECHANIC));
                continue;
            }

            mechanicsRepository.save(modelMapper.map(mechanic, Mechanic.class));

            stringBuilder.append(String.format(SUCCESSFUL_FORMAT,
                    MECHANIC,
                    mechanic.getFirstName(),
                    mechanic.getLastName()));
        }

        return stringBuilder.toString().trim();
    }
}
