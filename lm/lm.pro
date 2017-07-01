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
    src/auth.cpp

HEADERS  += src/mainwindow.h \
    src/loginwindow.h \
    src/sysconfig.h \
    src/server.h \
    src/inventory.h \
    src/auth.h \
    src/util.h

LIBS += -lboost_system
LIBS += -lcurl

FORMS    += src/mainwindow.ui \
    src/loginwindow.ui
