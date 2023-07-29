package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.AstronomerSeedDto;
import softuni.exam.models.dto.AstronomersWrapperDto;
import softuni.exam.models.entity.Astronomer;
import softuni.exam.models.entity.Star;
import softuni.exam.repository.AstronomerRepository;
import softuni.exam.repository.StarRepository;
import softuni.exam.service.AstronomerService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static softuni.exam.models.Constants.*;


@Service
public class AstronomerServiceImpl implements AstronomerService {

    private static final String ASTRONOMERS_FILE_PATH = "src/main/resources/files/xml/astronomers.xml";

    private AstronomerRepository astronomerRepository;
    private StarRepository starRepository;
    private ModelMapper modelMapper;
    private XmlParser xmlParser;
    private ValidationUtil validationUtil;

    public AstronomerServiceImpl(AstronomerRepository astronomerRepository,
                                 StarRepository starRepository,
                                 ModelMapper modelMapper, XmlParser xmlParser,
                                 ValidationUtil validationUtil) {
        this.astronomerRepository = astronomerRepository;
        this.starRepository = starRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
    }


    @Override
    public boolean areImported() {
        return this.astronomerRepository.count() > 0;
    }

    @Override
    public String readAstronomersFromFile() throws IOException {
        return Files.readString(Paths.get(ASTRONOMERS_FILE_PATH));
    }

    @Override
    public String importAstronomers() throws IOException, JAXBException {
        StringBuilder stringBuilder = new StringBuilder();

        List<AstronomerSeedDto> astronomers =
                xmlParser.fromFile(ASTRONOMERS_FILE_PATH, AstronomersWrapperDto.class).getAstronomers();


        for (AstronomerSeedDto astronomer : astronomers) {
            stringBuilder.append(System.lineSeparator());

            if (this.astronomerRepository.findFirstByFirstNameAndLastName
                    (astronomer.getFirstName(), astronomer.getLastName()).isPresent()
                    || !this.validationUtil.isValid(astronomer)) {
                stringBuilder.append(String.format(INVALID_FORMAT, ASTRONOMER));
                continue;
            }

            Optional<Star> star = this.starRepository.findById(astronomer.getObservingStarId());

            if (star.isEmpty()) {
                stringBuilder.append(String.format(INVALID_FORMAT, ASTRONOMER));
                continue;
            }

            Astronomer astro = this.modelMapper.map(astronomer, Astronomer.class);

            astro.setObservingStar(star.get());

            this.astronomerRepository.save(astro);

            stringBuilder.append(String.format(Locale.US, "Successfully imported astronomer %s %s - %.2f",
                    astronomer.getFirstName(),
                    astronomer.getLastName(),
                    astronomer.getAverageObservationHours()));
        }

        return stringBuilder.toString().trim();
    }
}
