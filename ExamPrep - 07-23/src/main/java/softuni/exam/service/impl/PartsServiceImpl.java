package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.PartImportDto;
import softuni.exam.models.entity.Part;
import softuni.exam.repository.PartsRepository;
import softuni.exam.service.PartsService;
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
public class PartsServiceImpl implements PartsService {
    private static String PARTS_FILE_PATH = "src\\main\\resources\\files\\json\\parts.json";
    private final PartsRepository partsRepository;
    private final Gson gson;
    private final ValidationUtilImpl validationUtil;
    private final ModelMapper modelMapper;

    public PartsServiceImpl(PartsRepository partsRepository,
                            Gson gson, ValidationUtilImpl validationUtil,
                            ModelMapper modelMapper) {
        this.partsRepository = partsRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return partsRepository.count() > 0;
    }

    @Override
    public String readPartsFileContent() throws IOException {
        return Files.readString(Path.of(PARTS_FILE_PATH));
    }

    @Override
    public String importParts() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        List<PartImportDto> parts = Arrays.stream(gson.fromJson
                        (readPartsFileContent(), PartImportDto[].class))
                .collect(Collectors.toList());

        for (PartImportDto part : parts) {
            stringBuilder.append(System.lineSeparator());

            if (partsRepository.findFirstByPartName(part.getPartName()).isPresent()
                    || !validationUtil.isValid(part)) {
                stringBuilder.append(String.format(INVALID_FORMAT, PART));
                continue;
            }

            if (validationUtil.isValid(part)) {

                partsRepository.save(modelMapper.map(part, Part.class));
                stringBuilder.append(String.format(SUCCESSFUL_FORMAT,
                        PART,
                        part.getPartName() + " -",
                        part.getPrice()));
            }
        }

        return stringBuilder.toString().trim();
    }
}
