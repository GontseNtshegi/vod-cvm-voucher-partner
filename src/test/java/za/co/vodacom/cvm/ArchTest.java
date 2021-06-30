package za.co.vodacom.cvm;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("za.co.vodacom.cvm");

        noClasses()
            .that()
            .resideInAnyPackage("za.co.vodacom.cvm.service..")
            .or()
            .resideInAnyPackage("za.co.vodacom.cvm.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..za.co.vodacom.cvm.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
