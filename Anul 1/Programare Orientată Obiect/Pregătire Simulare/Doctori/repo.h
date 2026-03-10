#include <vector>
#include "doctor.h"

using std::vector;

class Repository {
private:
	vector<Doctor> doctori;
	string filename;

public:
	Repository(const string& filename);
	~Repository() = default;
	const vector<Doctor>& getDoctori() const;
	void load_from_file();
};
