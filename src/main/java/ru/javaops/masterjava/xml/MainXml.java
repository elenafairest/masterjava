package ru.javaops.masterjava.xml;

import com.google.common.io.Resources;
import ru.javaops.masterjava.xml.schema.*;
import ru.javaops.masterjava.xml.util.JaxbParser;
import ru.javaops.masterjava.xml.util.StaxStreamProcessor;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class MainXml {
    private static final JaxbParser JAXB_PARSER = new JaxbParser(ObjectFactory.class);

    public static void printUsers(String projectName) throws IOException, JAXBException {
        Payload payload = JAXB_PARSER.unmarshal(
                Resources.getResource("payload.xml").openStream());
        payload.getUsers().getUser()
                .stream()
                .filter(user -> isBelongToProject(user, projectName))
                .sorted(Comparator.comparing(User::getFullName))
                .collect(Collectors.toList())
                .forEach(usr -> System.out.println(usr.getFullName() + " " + usr.getEmail()));
    }

    private static boolean isBelongToProject(User user, String projectName) {
        Set<Group> groups = new HashSet<>();
        user.getGroup().forEach(objectJAXBElement -> groups.add((Group) objectJAXBElement.getValue()));
        Set<Project> projects = new HashSet<>();
        groups.forEach(group -> projects.add((Project) group.getProject()));
        return projects
                .stream()
                .anyMatch(project -> projectName.equals(project.getProjectName()));
    }

    public static void printUsers2(String projectName) throws XMLStreamException, IOException {
        try (StaxStreamProcessor processor =
                     new StaxStreamProcessor(Resources.getResource("payload.xml").openStream())) {
            String user;
            while ((user = processor.getElementValue("User")) != null) {
                System.out.println(user);
            }
        }
    }

    public static void main(String[] args) throws JAXBException, IOException, XMLStreamException {
        printUsers2("topjava");
    }
}
