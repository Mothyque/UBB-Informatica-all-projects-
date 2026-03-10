#ifndef EXCEPTII_H
#define EXCEPTII_H

#include <stdexcept>
#include <string>

using std::string;
using std::exception;

class ValidareException : public exception
{
private:
	string mesaj;
public:
	explicit ValidareException(const string& mesaj) : mesaj{ mesaj } {}
	const char* what() const noexcept override
	{
		return mesaj.c_str();
	}
};

class DuplicatException : public exception
{
private:
	string mesaj;
public:
	explicit DuplicatException(const string& mesaj) : mesaj{ mesaj } {}
	const char* what() const noexcept override
	{
		return mesaj.c_str();
	}
};

class NotFoundException : public exception
{
private:
	string mesaj;
public:
	explicit NotFoundException(const string& mesaj) : mesaj{ mesaj } {}
	const char* what() const noexcept override
	{
		return mesaj.c_str();
	}
};

class InvalidCommandException : public exception
{
private:
	string mesaj;
public:
	explicit InvalidCommandException(const string& mesaj) : mesaj{ mesaj } {}
	const char* what() const noexcept override
	{
		return mesaj.c_str();
	}
};
#endif