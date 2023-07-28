package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.TaskImportDto;
import softuni.exam.models.dto.TaskWrapperDto;
import softuni.exam.models.entity.Car;
import softuni.exam.models.entity.Mechanic;
import softuni.exam.models.entity.Part;
import softuni.exam.models.entity.Task;
import softuni.exam.repository.CarsRepository;
import softuni.exam.repository.MechanicsRepository;
import softuni.exam.repository.PartsRepository;
import softuni.exam.repository.TasksRepository;
import softuni.exam.service.TasksService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static softuni.exam.models.Constants.*;

@Service
public class TasksServiceImpl implements TasksService {
    private static String TASKS_FILE_PATH = "src\\main\\resources\\files\\xml\\tasks.xml";

    private final TasksRepository tasksRepository;
    private final MechanicsRepository mechanicsRepository;
    private final PartsRepository partsRepository;
    private final CarsRepository carsRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;


    public TasksServiceImpl(TasksRepository tasksRepository,
                            MechanicsRepository mechanicsRepository,
                            PartsRepository partsRepository, CarsRepository carsRepository,
                            ModelMapper modelMapper,
                            XmlParser xmlParser,
                            ValidationUtil validationUtil) {
        this.tasksRepository = tasksRepository;
        this.mechanicsRepository = mechanicsRepository;
        this.partsRepository = partsRepository;
        this.carsRepository = carsRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return tasksRepository.count() > 0;
    }

    @Override
    public String readTasksFileContent() throws IOException {
        return Files.readString(Path.of(TASKS_FILE_PATH));
    }

    @Override
    public String importTasks() throws IOException, JAXBException {
        StringBuilder stringBuilder = new StringBuilder();

        List<TaskImportDto> tasks =
                xmlParser.fromFile(Path.of(TASKS_FILE_PATH).toFile(), TaskWrapperDto.class)
                        .getTasks();

        for (TaskImportDto task : tasks) {
            stringBuilder.append(System.lineSeparator());

            if (validationUtil.isValid(task)) {

                Optional<Mechanic> mechanic = mechanicsRepository
                        .findFirstByFirstName(task.getMechanic().getFirstName());

                Optional<Car> car =
                        carsRepository.findById(task.getCar().getId());

                Optional<Part> part = partsRepository.findById(task.getPart().getId());

                if (car.isEmpty() || part.isEmpty() || mechanic.isEmpty()) {
                    stringBuilder.append(String.format(INVALID_FORMAT, TASK));
                    continue;
                }

                Task taskToSave = modelMapper.map(task, Task.class);
                taskToSave.setMechanic(mechanic.get());
                taskToSave.setPart(part.get());
                taskToSave.setCar(car.get());

                tasksRepository.save(taskToSave);

                stringBuilder.append(
                        String.format(SUCCESSFUL_FORMAT,
                                TASK,
                                task.getPrice().setScale(2),
                                "").trim()
                );

                continue;
            }
            stringBuilder.append(String.format(INVALID_FORMAT, TASK));
        }

        return stringBuilder.toString().trim();
    }

    @Override
    public String getCoupeCarTasksOrderByPrice() {
        return null;
    }
}
