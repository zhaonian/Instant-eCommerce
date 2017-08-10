#-------------------------------------------------
#
# Project created by QtCreator 2017-06-22T10:57:59
#
#-------------------------------------------------

QT       += core gui network

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

TARGET = lm
TEMPLATE = app


SOURCES += src/main.cpp\
        src/mainwindow.cpp \
    src/loginwindow.cpp \
    src/sysconfig.cpp \
    src/server.cpp \
    src/inventory.cpp \
    src/auth.cpp \
    src/noselection.cpp \
    src/inventorycreation.cpp \
    src/inventorysingleselection.cpp \
    src/inventorymultipleselection.cpp \
    src/inventorytracking.cpp \
    src/inventoryinfo.cpp \
    src/workspace.cpp \
    src/inventorydb.cpp \
    test/test.cpp \
    test/testrunner.cpp \
    test/testauth.cpp \
    test/testworkspace.cpp \
    test/testinventory.cpp

HEADERS  += src/mainwindow.h \
    src/loginwindow.h \
    src/sysconfig.h \
    src/server.h \
    src/inventory.h \
    src/auth.h \
    src/util.h \
    src/noselection.h \
    src/inventorycreation.h \
    src/inventorysingleselection.h \
    src/inventorymultipleselection.h \
    src/inventorytracking.h \
    src/inventoryinfo.h \
    src/workspace.h \
    src/inventorydb.h \
    test/test.h \
    test/testrunner.h \
    test/testauth.h \
    test/testworkspace.h \
    test/testinventory.h

LIBS += -lboost_system
LIBS += -lcurl
LIBS += -pthread

FORMS    += src/mainwindow.ui \
    src/loginwindow.ui \
    src/noselection.ui \
    src/inventorycreation.ui \
    src/inventorysingleselection.ui \
    src/inventorymultipleselection.ui \
    src/inventorytracking.ui \
    src/inventoryinfo.ui
