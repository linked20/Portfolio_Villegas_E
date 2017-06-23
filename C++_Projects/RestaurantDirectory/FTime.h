// Villegas Eduardo
// 805 47 71 71
// CPSC 121, Spring 2013 Section 5/6
// HW Assignment 10
// Filename: FTime.h
// Credit: none

#pragma once
#include <string>
using namespace std;

//class FTime
class FTime
{
	friend void operator>>(istream& in, FTime& obj);
	friend ostream& operator<<(ostream&out, const FTime& obj);
private: 
	int hour;
	int min;
	int sec;

public:
	int GetHour() const;
	int GetMin()  const;
	int GetSec()  const;
	void GetTime(int& h, int& m, int& s);
	string GetStandard() const;
	string GetMilitary() const;
	void SetHour(int val);
	void SetMin(int val);
	void SetSec(int val);
	void SetTime(int h, int m, int sec);
	void SetTime(int h, int m);
	bool operator==(const FTime obj) const;
	bool operator>(const FTime obj) const;
	FTime& operator=(const FTime obj);




public:
	FTime(int h = 0, int m = 0, int s = 0);
	~FTime();
};

