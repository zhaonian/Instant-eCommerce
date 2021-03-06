#include "sysconfig.h"
#include "server.h"
#include "auth.h"
#include "loginwindow.h"
#include "mainwindow.h"
#include "ui_loginwindow.h"

static void
initialize_login_window_with_defaults(Ui::LoginWindow* ui)
{
        core::sysconfig* config = core::get_system_config();

        ui->login_database->setText(config->get_login_workspace().c_str());
        ui->login_username->setText(config->get_login_user_name().c_str());
        ui->login_password->setText(config->get_login_user_password().c_str());

        ui->hostname->setText(config->get_host_name().c_str());
        ui->portnumber->setText(std::to_string(config->get_host_port()).c_str());

        if (config->get_login_user_name().empty()) {
                ui->tabWidget->setCurrentIndex(1);
        } else {
                ui->tabWidget->setCurrentIndex(0);
        }
}

static void
connect_to_central_server(Ui::LoginWindow* ui)
{
        ui->statusbar->showMessage("Connecting...");
        std::string error;
        if (core::get_central_server()->connect(ui->hostname->text().toStdString(),
                                                std::stoi(ui->portnumber->text().toStdString()),
                                                error)) {
                ui->statusbar->showMessage(("Connected to the central server (" + core::get_central_server()->server_version() + ")").c_str());
                core::sysconfig* config = core::get_system_config();
                config->set_host_name(ui->hostname->text().toStdString());
                config->set_host_port(std::stoi(ui->portnumber->text().toStdString()));
        } else {
                ui->statusbar->showMessage(("Connection failure. cause: " + error).c_str());
                ui->tabWidget->setCurrentIndex(2);
        }
}

static void
save_login_form(Ui::LoginWindow* ui)
{
        core::sysconfig* config = core::get_system_config();
        config->set_login_workspace(ui->login_database->text().toStdString());
        config->set_login_user_name(ui->login_username->text().toStdString());
        config->set_login_user_password(ui->login_password->text().toStdString());
}

LoginWindow::LoginWindow(QWidget *parent, QDesktopWidget* desktop, MainWindow* main_win) :
        QMainWindow(parent),
        m_main_window(main_win),
        m_ui(new Ui::LoginWindow)
{
        m_ui->setupUi(this);

        QRect const& center = QStyle::alignedRect(Qt::LeftToRight, Qt::AlignCenter, this->size(), desktop->availableGeometry());
        this->setGeometry(center);

        initialize_login_window_with_defaults(m_ui);
        connect_to_central_server(m_ui);
}

LoginWindow::~LoginWindow()
{
        delete m_ui;
        delete m_main_window;
}

void
LoginWindow::on_login_button_clicked()
{
        m_ui->statusbar->showMessage("Logging in...");
        std::string error;
        core::identity identity = core::auth(*core::get_central_server(),
                                             m_ui->login_username->text().toStdString(),
                                             m_ui->login_password->text().toStdString(),
                                             m_ui->login_database->text().toStdString(),
                                             error);
        if (error.empty()) {
                save_login_form(m_ui);
                m_main_window->make_connection(identity);
                m_main_window->showMaximized();
                this->close();
        } else {
                m_ui->statusbar->showMessage(error.c_str());
        }
}

void
LoginWindow::on_connect2server_clicked()
{
        connect_to_central_server(m_ui);
}

void
LoginWindow::on_signup_button_clicked()
{
        m_ui->statusbar->showMessage("Creating account...");
        std::string error;
        core::auth_create(*core::get_central_server(),
                          m_ui->signup_username->text().toStdString(),
                          m_ui->signup_password->text().toStdString(),
                          error);
        if (error.empty()) {
                m_ui->statusbar->showMessage("Account has been created.");
                m_ui->login_database->setText("imaginarydb");
                m_ui->login_username->setText(m_ui->signup_username->text());
                m_ui->login_password->setText(m_ui->signup_password->text());
                m_ui->tabWidget->setCurrentIndex(0);
        } else {
                m_ui->statusbar->showMessage(error.c_str());
        }
}
