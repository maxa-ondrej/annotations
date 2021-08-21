package cz.majksa.tests.annotations;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Set;

@SupportedAnnotationTypes(ConfigProcessor.ANNOTATION)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ConfigProcessor extends AbstractProcessor {

    public static final String ANNOTATION = "cz.majksa.tests.annotations.Config";
    private static int fileNameCounter = 0;

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        annotations.stream()
                .map(roundEnv::getElementsAnnotatedWith)
                .forEach(elements -> elements.forEach(element -> {
                    try (final BufferedWriter writer = createWriter()) {
                        writer.write(element.getClass().getName());
                    } catch (IOException e) {
                        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage());
                    }
                }));
        return true;
    }

    private BufferedWriter createWriter() throws IOException {
        final String filename = ANNOTATION + ++fileNameCounter;
        FileObject obj = processingEnv.getFiler().createResource(StandardLocation.CLASS_OUTPUT, "", filename);
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Writing " + filename + " to " + obj.toUri());
        return new BufferedWriter(obj.openWriter());
    }

}
