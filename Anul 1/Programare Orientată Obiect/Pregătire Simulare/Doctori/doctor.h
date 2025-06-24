#include <string>

using std::string;

class Doctor
{
private:
	string cnp;
	string prenume;
	string sectie;
	bool concediu;

public:
	Doctor(const string& cnp, const string& prenume, const string& sectie, bool concediu);
	~Doctor() = default;

	const string& getCnp() const;
	const string& getPrenume() const;
	const string& getSectie() const;
	bool getConcediu() const;

	void setCnp(const string& cnp_nou);
	void setPrenume(const string& prenume_nou);
	void setSectie(const string& sectie_nou);
	void setConcediu(bool concediu_nou);

};
