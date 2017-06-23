// Villegas Eduardo
// 805 47 71 71
// CPSC 121, Spring 2013 Section 5/6
// HW Assignment 10
// Filename: HW10.cpp
// Credit: none
#include<iostream>
#include<iomanip>
#include "Restaurant.h"
#include"FTime.h"

using namespace std;

enum{ ADD = 1, PRINT, QUIT};
const int MAX=100;
int ReadMenu();
void DisplayMenuChoice(); 
void ProcessChoice( int choice, Restaurant rest[], int& count);
void AddRestaurant(Restaurant rest[], int& count);
void PrintAll(const Restaurant rest[], int count);
string ReadTitle();
int ReadRatings();
void WriteLine(char ch, int size);
bool ReadInt(int& val);
void ReadHour(int& hour, int& min, string prompt);
int main()
{
	Restaurant rest[MAX];
	int choice;
	int count=0;

	while(QUIT != (choice = ReadMenu()))
	{
		ProcessChoice(choice, rest, count);
	}

	return 0;

}

//Function name:ReadMenu
//Purpose:to obtain and validate int dec and process choice in regards to menu
//Input:none
//Output:none
//In Param:none
//Out Param:none
//Return: dec
//Function Called:DisplayMenuChoice(), ReadInt(int)
int ReadMenu()
{
	int dec;
	bool valid;

	DisplayMenuChoice();
	do{
		valid = ReadInt(dec);
		if(valid)
			if( dec>= ADD && dec <= QUIT)
				valid = true;
			else
			{
				cout << "Invalid Entry Try Again" << endl;
				valid = false;
			}
	}while(!valid);

	return dec;

}

//Function name: DisplayMenuChoice()
//Purpose: To display a menu with possible int choices
//Input:none
//Output:none
//In Param:none
//Out Param:none
//Return:none
//Function Called:none
void DisplayMenuChoice()
{
	cout << left << setw(5);
	cout << "MENU" << endl;
	WriteLine('-',70);
	cout << left << setw(10);
	cout << "1. Add Restaurant" << endl;
	cout << left << setw(10);
	cout << "2. Print All" << endl;
	cout << left << setw(10);
	cout << "3. Exit" << endl;
	WriteLine('-',70);
	cout << left << setw(10);
	cout << "Enter 1-3:";
}

//Function name:ProcessChoice
//Purpose:to conduct switch and process int dec
//Input:none
//Output:none
//In Param:Restaurant rest[], count, choice
//Out Param:Restaurant rest[], count
//Return:none
//Function Called:AddRestaurant(rest, ratings, count), Print(rest, ratings, count)

void ProcessChoice(int choice, Restaurant rest[], int& count)
{
			switch(choice)
			{
			case ADD  : AddRestaurant(rest, count); break;
			case PRINT: PrintAll(rest, count); break;
			default   : cout << "Invalid Entry Try Again"; break; 
			}

}

//Function name:AddRestaurant
//Purpose:to add restaurants into array
//Input:none
//Output:none
//In Param:count, 
//Out Param:Restaurant rest[], count
//Return:none
//Function Called:AddRestaurant(titles, ratings, count), Print(titles, ratings, count)
void AddRestaurant(Restaurant rest[], int& count)
{
	int openh, openm, closeh, closem;
	FTime ty, dy;
	if (count < MAX)
	{
		rest[count].SetName(ReadTitle()); 
		rest[count].SetRating(ReadRatings());
		ReadHour(openh, openm, "Opening Hour (Military Time)");
		rest[count].SetOpen(openh, openm);
		ReadHour(closeh, closem, "Closing Hour (Military Time)");
		rest[count++].SetClose(closeh, closem);
	}
	else
	{ 
		cout << "Full" << endl;
	}
}

//Function name:ReadTitle
//Purpose:to obtain and validate a restaurant name
//Input:title
//Output:none
//In Param:none
//Out Param:none
//Return:string
//Function Called:
string ReadTitle()
{
	string name;
	bool valid;

	cout << endl;
	WriteLine('=',70);
	cout << left << setw(5);
	cout << "ADD Restaurant" << endl;
	cout << left << setw(5);
	WriteLine('-',70);
	cout << "Name: ";
	do{
		getline(cin,name);
		if(name =="")
		{
			valid = false;
		}
		else
		{
			valid = true;
		}
	}while(!valid);

	return name;

}

//Function name:ReadRatings
//Purpose:to obtain and validate a rating 
//Input: none
//Output:none
//In Param:none
//Out Param:none
//Return:int
//Function Called:ReadInt
int ReadRatings()
{
	bool valid;
	int rate;

	cout << left << setw(5);
	cout << "Rating out of 5: ";
	do{
		valid = ReadInt(rate);
		if(valid)
		{
			if(rate <= 0 || rate > 5)
			{
				cout << "Invalid Entry Try Again" << endl;
				valid = false;
			}
			else
			valid = true;
		}
	}while(!valid);

	return rate;
}	

//Function name:ReadInt
//Purpose:to validate a number
//Input:val
//Output:none
//In Param:val
//Out Param:val
//Return:bool
//Function Called:none
bool ReadInt(int& val)
{
	bool valid;

	cin >> val;
	if(cin.fail())
	{
		valid = false;
		cin.clear();
	}
	else
		valid = true;
	cin.ignore(numeric_limits<streamsize>::max(),'\n');

	return valid;
}

//Function name:PrintAll
//Purpose:To print accumulated restaurant names, ratings and hours of operation
//Input:none
//Output:titles, ratings
//In Param:titles, ratings, count
//Out Param:none
//Return:none
//Function Called:none
void PrintAll(const Restaurant rest[], int count)
{
	WriteLine('=',70);
	cout << "Restaurants" << endl;
	WriteLine('-',70);
	cout << left << setw(15)
		 << "Name" 
		 << right << setw(5)
		 << "Rating"
		 << right << setw(10)
		 << " Open"
		 << right << setw(10)
		 << " Close" << endl;
	
	for(int i = 0; i < count; i++)
	{
		cout << left << setw(15)
			 << rest[i].GetName()
			 << right << setw(5)
			 << rest[i].GetRating()
			 << right << setw(10)
			 << rest[i].GetOpen()
			 << right << setw(10)
			 << rest[i].GetClose() << endl;
	}
	cout << endl;
}


//Function name:WriteLine
//Purpose:to write a line with different style character
//Input:none
//Output:ch
//In Param:ch, size
//Out Param:none
//Return:none
//Function Called:none
void WriteLine(char ch, int size)
{

	cout << setfill(ch) << setw(size) << "" << endl;
	cout << setfill(' ');
}

//Function name:ReadHour
//Purpose:to obtain an hour for a restaurant
//Input:hour, min
//Output:prompt
//In Param:none
//Out Param:hour, min
//Return:void
//Function Called:none
void ReadHour(int& hour, int& min, string prompt)
{
	bool valid;

	do
	{
		cout << prompt << "(hh:mm)";
		cin >> hour;
		cin.ignore();
		cin >> min; 

		if(cin.fail())
		{
			valid = false;
			cout << "hh:mm only" << endl;
			cin.clear();
		}
		else if (hour < 0 || hour > 23 ||
				 min  < 0 || min  > 59 )
		{
			valid = false;
			cout << "Invalid Time" << endl;
		}
		else
		{
			valid = true;
		}
		cin.ignore(1000, '\n');
	}while(!valid);
}