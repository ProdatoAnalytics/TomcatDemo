package com.prodato.norma.demo.tomcat.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.io.FileUtils;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;


public class MenuLoader {

    private final static String[] XML_EXTENSIONS = { "xml" };

    private final File menuFolder;

    private final List<Menu> menus;

    public MenuLoader(final File menuFolder) {
        this.menuFolder = menuFolder;
        final Collection<File> xmlFiles = FileUtils.listFiles(menuFolder, XML_EXTENSIONS, false);
        final Set<File> xmlFilesSorted = new TreeSet<>(new Comparator<File>() {
            @Override
            public int compare(final File f1, final File f2) {
                return f1.getName().compareTo(f2.getName());
            }
        });
        xmlFilesSorted.addAll(xmlFiles);

        final List<Menu> menus = new ArrayList<>();
        try {
            final JAXBContext jaxbContext = JAXBContext.newInstance(Menu.class);
            final Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            for (final File file : xmlFilesSorted) {
                final Menu menu = (Menu) jaxbUnmarshaller.unmarshal(file);
                menus.add(menu);
            }
        } catch (final JAXBException e) {
            e.printStackTrace();
        }
        this.menus = Collections.unmodifiableList(menus);
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public File getMenuFolder() {
        return menuFolder;
    }

}
