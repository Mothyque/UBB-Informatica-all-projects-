#include "StudentTableModel.h"
#include <QBrush>
StudentTableModel::StudentTableModel(const vector<Student>& studenti) : studenti(studenti) {}

void StudentTableModel::setStudenti(const vector<Student>& s)
{
	// Seteaza studentii in tabela
	// parametrii: s - vectorul de studenti
	studenti = s;
	emit layoutChanged();
}

int StudentTableModel::rowCount(const QModelIndex& parent) const
{
	// Returneaza numarul de linii
	// returns: numarul de linii
	return static_cast<int>(studenti.size());
}

int StudentTableModel::columnCount(const QModelIndex& parent) const 
{
	// Returneaza numarul de coloane
	// returns: numarul de coloane
	return 4;
}

QVariant StudentTableModel::data(const QModelIndex& index, int role) const
{
	// Incarca datele in tabel
	// parametri: index - indexul din tabel la care incarcam 
	// role - role ul
	const Student& student = studenti.at(index.row());
	if (role == Qt::DisplayRole)
	{
		switch (index.column())
		{
		case 0: return QString::number(student.getNrMatricol());
		case 1: return QString::fromStdString(student.getNume());
		case 2: return QString::number(student.getVarsta());
		case 3: return QString::fromStdString(student.getFacultate());
		}
	}
	if (role == Qt::BackgroundRole)
	{
		if (student.getFacultate() == "mate")
		{
			return QBrush(Qt::red);
		}
		if (student.getFacultate() == "info")
		{
			return QBrush(Qt::blue);
		}
		if (student.getFacultate() == "mate-info")
		{
			return QBrush(Qt::green);
		}
		if (student.getFacultate() == "ai")
		{
			return QBrush(Qt::yellow);
		}
	}
	return QVariant();
}


QVariant StudentTableModel::headerData(int section, Qt::Orientation orientation, int role) const
{
	// Incarca header ele 
	// parametrii: section - coloana pe care punem headerul
	//			   orientation - orientarea headerului
	//			   role - role ul
	if (role == Qt::DisplayRole && orientation == Qt::Horizontal)
	{
		switch (section)
		{
		case 0: return "Numar matricol";
		case 1: return "Nume";
		case 2: return "Varsta";
		case 3: return "Facultate";
		}
	}
	return QVariant();
}