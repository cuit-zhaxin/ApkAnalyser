/*
 * Copyright (C) 2012 Sony Mobile Communications AB
 *
 * This file is part of ApkAnalyser.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package andreflect.gui.action;

import gui.actions.AbstractCanceableAction;

import java.awt.event.ActionEvent;

import javax.swing.Icon;

import analyser.gui.MainFrame;
import analyser.gui.Selection;
import analyser.logic.RefContext;
import andreflect.ApkClassContext;
import andreflect.xml.XmlResourceChecker;

public class XmlUnusedResourceAction extends AbstractCanceableAction {

    private static final long serialVersionUID = 46486544661944141L;

    protected static XmlUnusedResourceAction m_inst = null;

    public static XmlUnusedResourceAction getInstance(MainFrame mainFrame) {
        if (m_inst == null) {
            m_inst = new XmlUnusedResourceAction("Find resource references", null);
            m_inst.setMainFrame(mainFrame);
        }
        return m_inst;
    }

    protected XmlUnusedResourceAction(String actionName, Icon actionIcon) {
        super(actionName, actionIcon);
    }

    @Override
    public String getWorkDescription() {
        return "Finding resource references";
    }

    @Override
    public void handleThrowable(Throwable t) {
        t.printStackTrace();
        getMainFrame().showError("Error finding resource references", t);
    }

    @Override
    public void run(ActionEvent e) throws Throwable {
        Object oRef = Selection.getRefContextOfSeletedObject();
        if (oRef == null || !(oRef instanceof RefContext)) {
            return;
        }
        RefContext cRef = (RefContext) oRef;

        if (cRef.getContext().getContextDescription().equals(ApkClassContext.DESCRIPTION)) {
            ApkClassContext apkContext = (ApkClassContext) cRef.getContext();
            XmlResourceChecker checker = apkContext.getXmlParser().getResourceChecker();
            checker.showUnusedSpec(apkContext, (MainFrame) getMainFrame(), this);
        }
    }

}
