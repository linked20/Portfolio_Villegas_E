
//Villegas, Eduardo	
//805 47 71 71
//CPSC121 Section 5/6 Spring 2013
//Homework #5
//Filename:HW05
//Credits:
#include<iostream>
#include<fstream>
#include<string>
#include<iomanip>

using namespace std;
const int MAX = 10;
void ReadInput(string& first,string& last, double scores[], double& totalscores1, int& count);
void ReadName(string prompt, string& name);
double ReadScore(double& totsco);
void FindMin(double& min, int count, double scores[]);
void FindMax(double& max, int count, double scores[]);
void FindAVG(double& avg, int count, double totalscores);
char GradeLetter(double avg);
void WriteOutput(string first, string last, double scores[], int count, char grade, double max, double min, double avg);
void WriteGradeReport(ostream& out, string first, string last, double scores[], int count, char grade, double max, double min, double avg);
bool ReadDouble(double& val);
bool ReadChar(char& ch);
bool HaveMore();
void WriteLine(ostream& out, char sign, int size);


int main()
{
	do{
		string first, last;
		double scores[9] = {0.0};
		int count = 0;
		double totalscores= 0;
		double max, min, avg = 0;
		char grade;


		ReadInput(first, last, scores, totalscores, count);
		FindMin(min, count, scores);
		FindMax(max, count, scores);
		FindAVG(avg, count, totalscores);
		grade = GradeLetter(avg);
		WriteOutput(first, last, scores, count, grade, max, min, avg);
		cout << "Do you have more students? (Y/N)" << endl;
	}while(!HaveMore());

	
	return 0;
}
void ReadInput(string& first, string& last, double scores[], double& totalscores1, int& count)
{
	bool valid;
	char yesno;
	count=0;


	ReadName("First name", first);
	ReadName("Last name", last);
	
	do
	{
	cout << "Enter a number: ";
	scores[count++]=ReadScore(totalscores1);
	cout << "More?(Y/N)";
	cin >> yesno;	
	} while ('Y' == toupper(yesno) && count < MAX);
		
}

void ReadName(string prompt, string& name)
{

	cout << prompt << " : ";
	cin >> name;
}

double ReadScore(double& totsco)
{
	bool valid;
	double score;
	
	do{
		
		valid = ReadDouble(score);
		if(valid)
		{
				if(score > 100 || score < 0)
			{
				valid = false;
				cout << "Invalid Entry. Try Again. \n";

			}
			else
			{
				valid=true;
				totsco += score;
			}
		}
			
	}while( !valid);

	return score;
}


void FindMin(double& min, int count, double scores[])
{
	min=scores[0];
	
	for(int i = 1; i < count; i++)
	 if (min > scores[i])
		min = scores[i];
}
void FindMax(double& max, int count, double scores[])
{
	max=scores[0];
	
	for(int i = 1; i < count; i++)
		if(max < scores[i])
			max = scores[i];
}

void FindAVG(double& avg, int count, double totalscores)
{
	avg = totalscores/static_cast<float>(count);
}
char GradeLetter(double avg)
{
	char grade1;
	
	if(avg > 90)
		grade1 = 'A';
	else if(avg > 80)
		grade1 = 'B';
	else if(avg > 70)
		grade1 = 'C';
	else if(avg > 60)
		grade1 = 'D';
	else
		grade1 = 'F';
	
	return grade1;
}
void WriteOutput(string first, string last, double scores[], int count, char grade, double max, double min, double avg)
{
	string filename;
	
	filename = last + "," + first + "'s Homework Grades.txt";
	ofstream outfile; 
	outfile.open(filename);
	WriteGradeReport(cout, first, last, scores, count, grade, max, min, avg);
	WriteGradeReport(outfile, first, last, scores, count, grade, max, min, avg);
}

void WriteGradeReport(ostream& out, string first, string last, double scores[], int count, char grade, double max, double min, double avg)
{
	out << left << setw(5) << "HOMEWORK" << endl;
	WriteLine(out,'-', 70);
	out << left << setw(5);
	out << last << ", " << first << endl;
	WriteLine(out, '-', 70);
	for(int i = 0; i < count; i++)
	{
		out << fixed << showpoint << setprecision(1);
		out << left << setw(5);
		out << scores[i] << " " << endl;
	}
	cout << fixed << showpoint << setprecision(1);
	WriteLine(out, '-', 70);
	out << left << setw(5) << "MAX";
	out << right << setw(65) << max << endl;
	out << left << setw(5) << "MIN";
	out << right << setw(65) << min << endl;
	out << left << setw(5) << "MEAN";
	out << right << setw(65) << avg << endl;
	WriteLine(out, '-', 70);
	out << left << setw(5) << "GRADE";
	out << right << setw(65) << grade << endl;
	WriteLine(out, '-', 70);


}
bool ReadDouble(double& val)
{
	bool valid;
	cin >> val;
	if(cin.fail())
	{
		cout << "Invalid Entry Try Again" << endl;
		valid = false;
		cin.clear();
	}
	else
		valid = true;
	cin.ignore(1000,'\n');
	return valid;
}
// was not able to fix this as you wanted too confused by your notes
bool ReadChar(char& ch)
{
	bool valid=true;
	cin >> ch;
	ch = toupper(ch);
	cin.ignore(1000, '\n');
	return valid;

}

bool HaveMore()
{
	bool valid;
	char more;
	do{
		valid = ReadChar(more);
		if(more !='Y' && more != 'N')
			valid = false;
		else
			valid = true;
	}while(!valid);
		return valid;
}

void WriteLine(ostream& out, char sign, int size)
{
	out << setfill(sign) << setw(size) << "" << endl;
	out << setfill(' ');
}