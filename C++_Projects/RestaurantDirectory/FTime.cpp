// Villegas Eduardo
// 805 47 71 71
// CPSC 121, Spring 2013 Section 5/6
// HW Assignment 10
// Filename: FTime.cpp
// Credit: none

#include "FTime.h"
#include <iostream>
#include <sstream>
#include <iomanip>
#include<istream>
#include<stdexcept>

using namespace std;

void operator>>(istream& in, FTime& obj)
{
	//int hour, min, sec;
	in >> obj.hour;

	if( obj.hour < 0 || obj.hour > 23)
		throw runtime_error("Invalid Hour");
	in.ignore();
	
	in >> obj.min; 
	if(obj.min < 0 || obj.hour > 59)
		throw runtime_error("Invalid Minute");
	in.ignore();
	/*
	in >> obj.sec;
	if(obj.sec < 0 || obj.sec > 59)
		throw runtime_error("Invalid Second");*/

	if(in.fail())
	{
		in.clear();
		throw runtime_error("Invalid Format");
	}
}
ostream& operator<<(ostream&out, const FTime& obj)
{
	out << obj.GetStandard();

	return out;
}

bool FTime::operator>(const FTime obj) const
{
	if(hour > obj.hour)
	{
		return true;
	}
	else if( hour == obj.hour)
	{
		if( min > obj.hour)
		{
			return true;
		}
	}
	else 
	{
		return false;
	}
}

bool FTime::operator==(const FTime obj) const
{
	if( hour == obj.hour && min == obj.min)
		return true;
	else 
		return false;
}

FTime& FTime::operator=(const FTime obj) 
{
	hour = obj.hour;
	min = obj.hour;
	//sec = obj.min;

	return *this;
} 

FTime::FTime(int h, int m, int s)
{
	SetHour(h);
	SetMin(m);
	SetSec(s);
}


FTime::~FTime()
{
	
}

int FTime::GetHour() const
{
	return hour;
}
int FTime::GetMin() const
{
	return min;
}
int FTime::GetSec() const
{
	return sec;
}
string FTime::GetStandard() const
{
	stringstream ss;

	int h = hour % 12;

	ss << setfill('0') 
	   << setw(2) << (h == 0 ? 12 : h) << ":"
	    << setw(2) << min  /*<< ":"
	    << setw(2) << sec */<< (hour >= 12 ? " PM" : " AM");

	return ss.str();
}
string FTime::GetMilitary() const
{
	stringstream ss;

	ss << setfill('0') 
	   << setw(2) << hour << ":"
	   << setw(2) << min  << ":"
	   << setw(2) << sec;

	return ss.str();
}
void FTime::GetTime(int& h, int& m, int& s)
{
	h = hour;
	m = min;
	s = sec;
}

void FTime::SetHour(int val)
{
	hour = val;

	hour = (val > 23 || val < 0) ? 0 : val;  // ? means set to 
}
void FTime::SetMin(int val)
{
	min = val;
	
	min = (val > 59 || val < 0) ? 0 : val;
}
void FTime::SetSec(int val)
{
	sec = val;

	sec = (val > 59 || val < 0) ? 0 : val;
}
void FTime::SetTime(int h, int m, int s)
{
	SetHour(h);
	SetMin(m);
	SetSec(s);
}

void FTime::SetTime(int h, int m)
{
	SetHour(h);
	SetMin(m);
}
