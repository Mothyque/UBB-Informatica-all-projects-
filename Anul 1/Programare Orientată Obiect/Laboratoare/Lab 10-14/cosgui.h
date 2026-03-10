#pragma once
#include <QMessageBox>
#include <QPushButton>
#pragma once
#include <qtableview.h>
#include <qtablewidget.h>
#include <QWidget>

#include "observable.h"
#include "repo.h"

class CosGUI : public QWidget
{
private:
	CosInchiriere& cos;
	QTableWidget* tableCos;
	QPushButton* btnAdauga;
	QPushButton* btnGoleste;
	QPushButton* btnGenerare;
	QPushButton* btnExporta;
	QLineEdit* numeFisierExporta;
	QLineEdit* numeFilm;
	QLineEdit* numeRegizor;
	QSlider* slider;
	QLabel* sliderValueLabel;
	void initGui();
	void connectSignalsAndSlots();

public:
	CosGUI(CosInchiriere& cos, QWidget* parent = nullptr);
	~CosGUI() = default;
	void reload();
};

