#ifndef LOGINWINDOW_H
#define LOGINWINDOW_H

#include <QMainWindow>
#include <QDesktopWidget>
#include "mainwindow.h"

namespace Ui {
class LoginWindow;
}

class LoginWindow : public QMainWindow
{
        Q_OBJECT

public:
        explicit LoginWindow(QWidget *parent, QDesktopWidget* desktop, MainWindow* main_win);
        ~LoginWindow();

private slots:
        void on_login_button_clicked();

        void on_connect2server_clicked();

        void on_signup_button_clicked();

private:
        MainWindow*             m_main_window;
        Ui::LoginWindow*        m_ui;
};

#endif // LOGINWINDOW_H
