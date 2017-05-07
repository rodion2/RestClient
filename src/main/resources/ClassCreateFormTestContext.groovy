import client.gui.ClassCreateForm
import client.gui.FindClassesWidget
import client.gui.FunctionCreateForm
import client.gui.MainWindow
import client.gui.WinApiHandbookReactor
import client.service.WinApiHandbookRestClient
import org.springframework.beans.factory.support.LookupOverride
import org.springframework.beans.factory.support.MethodOverrides

beans {
//    handbookService(DummyHandbookService) { bean ->
//        bean.initMethod = 'setup'
//        files = new HashMap<>()
//        topics = [1l: new WinApiClass(1l,"class1", "","",[])]
//    }

    handbookService(WinApiHandbookRestClient) { bean ->
//        bean.initMethod = 'setup'
//        host = 'localhost'
//        port = 8080
    }

    reactor(WinApiHandbookReactor, handbookService)

    functionCreateForm(FunctionCreateForm, reactor){
        bean -> bean.scope = 'prototype'
    }

    classCreateForm(ClassCreateForm, reactor) { bean ->
        bean.methodOverrides = new MethodOverrides()
        bean.methodOverrides.addOverride(new LookupOverride('functionCreateForm', 'functionCreateForm'))
    }

    searchPanel(FindClassesWidget, reactor)

    mainWindow(MainWindow, reactor, classCreateForm, searchPanel)
}
