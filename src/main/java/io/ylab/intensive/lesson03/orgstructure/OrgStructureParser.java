package io.ylab.intensive.lesson03.orgstructure;

import java.io.File;
import java.io.IOException;

/**
 * @author Denis Zolotarev
 */
public interface OrgStructureParser {
    Employee parseStructure(File csvFile) throws IOException;
}
