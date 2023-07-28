package softuni.exam.util;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;


public interface XmlParser {
        <T> T fromFile(File file, Class<T> tClass) throws JAXBException, FileNotFoundException;
    }

