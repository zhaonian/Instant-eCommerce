#include "mainwindow.h"
#include "loginwindow.h"
#include <QApplication>
#include <QDesktopWidget>
#include <QStyle>



static int
startup(QApplication const& app)
{
        LoginWindow w(nullptr, app.desktop(), new MainWindow());
        w.show();
        return app.exec();
}

int main(int argc, char *argv[])
{
        QApplication app(argc, argv);
        return startup(app);
}
