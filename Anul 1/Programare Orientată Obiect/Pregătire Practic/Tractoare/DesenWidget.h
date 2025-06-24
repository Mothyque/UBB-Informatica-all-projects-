#pragma once
#include <QWidget>
#include <QPainter>
#include <QMouseEvent>

class DesenWidget : public QWidget
{
	Q_OBJECT
private:
	int nrRoti = 0;
	QVector<QRectF> cercuri;

signals:
	void cercClicked();

public:
	DesenWidget() = default;
	~DesenWidget() override = default;
	void setNrRoti(int nrRotiNou) { this->nrRoti = nrRotiNou; update(); }
	void paintEvent(QPaintEvent*) override {
		QPainter p(this);
		p.setBrush(Qt::red);

		int radius = 10;
		int x = 10;
		int y = height() / 2;

		for (int i = 0; i < nrRoti; ++i) {
			QRectF cerc(x + i * (2 * radius + 10) - radius, y - radius, 2 * radius, 2 * radius);
			cercuri.push_back(cerc);
			p.drawEllipse(cerc);
		}
	}
	void mousePressEvent(QMouseEvent* event) override
	{
		QPointF clickPos = event->pos();
		for (const auto& cerc : cercuri)
		{
			if (cerc.contains(clickPos))
			{
				emit cercClicked();
				break;
			}
		}
	}

};
