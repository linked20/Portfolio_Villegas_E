// Villegas Eduardo
// 805 47 71 71
// CPSC 121, Spring 2013 Section 5/6
// HW Assignment 10
// Filename: Restaurant.h
// Credit: none
#pragma once
#include <string>
#include"FTime.h"

using namespace std;

class Restaurant
{
private:

	string name;
	int rating;
	FTime closingHour;
	FTime openingHour;

public:

	void Rating(int& val);
	int GetRating() const;
	string GetName() const;
	void SetRating(int val);
	void SetName(string val);
	void SetOpen(int h, int m);
	void SetClose(int h, int m);
	void SetOpen(FTime time);
	string GetOpen()const;
	string GetClose()const;
	void GetHours(string& open, string& close)const;

public:
	Restaurant(int r);
	Restaurant(void);
	~Restaurant(void);
};

