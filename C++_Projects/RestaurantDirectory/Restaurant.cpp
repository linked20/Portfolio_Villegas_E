// Villegas Eduardo
// 805 47 71 71
// CPSC 121, Spring 2013 Section 5/6
// HW Assignment 10
// Filename: Restaurant.cpp
// Credit: none
#include "Restaurant.h"
#include "FTime.h"
#include <iostream>
#include <sstream>
#include <iomanip>
#include<istream>
#include<stdexcept>
#include<string>

using namespace std;

Restaurant::Restaurant(int r=0)
{
	rating = r; 
}

Restaurant::Restaurant(void)
{ 
}
void Restaurant::SetRating(int val)
{
	rating = val;
}

void Restaurant::SetName(string val)
{
	name = val;
}

string Restaurant::GetName()const
{
	return name;
}

int Restaurant::GetRating()const
{
	return rating;
}

void Restaurant::SetOpen(int hour, int minute)
{
	openingHour.SetTime(hour, minute);
}

void Restaurant::SetClose(int hour, int minute)
{
	closingHour.SetTime(hour, minute);
}

void Restaurant::SetOpen(FTime time)
{
	time = openingHour; 
}

string Restaurant::GetClose() const
{
	return closingHour.GetStandard();
}

string Restaurant::GetOpen() const
{
	return openingHour.GetStandard();
}

void Restaurant::GetHours(string& open, string& close)const
{

	open = openingHour.GetStandard();
	close = closingHour.GetStandard();
}

Restaurant::~Restaurant(void)
{
}
