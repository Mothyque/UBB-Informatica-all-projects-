#include "cosReadOnlyGui.h"
#include <QPainter>
CosReadOnlyGui::CosReadOnlyGui(CosInchiriere& cos) : cos(cos)
{
	/* Constructorul clasei CosReadOnlyGui
	 * parameter cos: referinta la obiectul de tip CosInchiriere
	 */
	cos.addObserver(this);
	setWindowTitle("Cos Filme");
}

void CosReadOnlyGui::actualizeaza()
{
	/* Actualizeaza interfata grafica */
	update();
	resize(400, 300);
}

void CosReadOnlyGui::paintEvent(QPaintEvent* event) 
{
	/* Deseneaza filmele in cos */
	(void)event;
	QPainter painter(this);
	int nr = cos.getNrFilme();
	for (int i = 0; i < nr; ++i)
	{
		Film film = cos.getFilme()[i];
		{
			int x = rand() % width();
			int y = rand() % height();
			int imagine = rand() % 7;
			string filename[7] = {"skibidi.jpeg", "baskemtball.png", "sunshine.jpeg", "nicusor.jpeg", "mickey.jpeg","gamble.jpeg", "dog.jpg"};
			painter.drawImage(x, y, QImage(QString::fromStdString(filename[imagine])));
		}
	}
}


