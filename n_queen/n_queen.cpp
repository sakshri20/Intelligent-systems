/*
 Project 2: Random Restart Hill Climbing algorithm to solve N Queen Problem
 Name: Sakshi Shrivastava
 Student ID: 800987926
 Email: sshriva3@uncc.edu
 File Name: n_queen.cpp
 */

#include "n_queen.hpp"
#include <iostream>
#include <stdlib.h>
#include <time.h>
using namespace std;

void randomStates(int *, int);
void nQueen(int);
int heuristic(int *, int);
void print(int *, int);
int nextState(int *, int);
int* createState(int *, int);
int statesCount = 0;                        //used to store number of states created
int restartCount = 0;                      //used to store number of random restarts


int main() {
    int n;
    cout << "Enter the number of queens: "<<endl;
    cin>>n;
    
    nQueen(n);                           //calling nQueen function to solve the problem
    return 0;
}

// The function solves the N Queen Problem by using hill climbing and displays the number
// of states and number of random restarts.
void nQueen(int size)
{
    int *board = new int[size];
    srand(time(NULL));
    randomStates(board, size);
    
    while(heuristic(board,size) != 0)
    {
        if(nextState(board,size) == 0)
        {
            randomStates(board, size);
        }
    }
    
    cout<<"\n Solution found!!!\n";
    cout<<"\n Number of states = "<<statesCount<<endl;
    cout<<"\n Number of restarts = "<<restartCount<<endl;
    print(board,size);
    
}

// The function generates a board arranging queens randomly
void randomStates(int *board, int size)
{
    restartCount++;
    
    for(int i=0; i<size; i++)
    {
        board[i] = rand() % size;
    }
}

// The function calculates the heuristic value of the whole board
int heuristic(int *board, int size)
{
    int h = 0;
    
    for(int i=0; i<size; i++)
    {
        for(int j=i+1; j<size; j++)
        {
            if(board[i] == board[j])                    // Checking same column or not
                h++;
            else if((board[i]-board[j]) == (i-j))        // Checking diagonal
                h++;
            else if((board[i]-board[j]) == (j-i))        // Checking diagonal
                h++;
        }
    }
    return h;                                            // return total number of conflicts
}

// The function checks whether the new state created is beter than previous state or not
int nextState(int *board, int size)
{
    int *temp = new int[size];
    int h = heuristic(board,size);
    int i;
    
    temp = createState(board,size);            // Creating a new state
    if(heuristic(temp,size) < h)
    {
        for(i=0; i<size; i++)
            board[i] = temp[i];
        return 1;
    }
    return 0;
}

// The function creates a new state by moving a queen to best possible place and returns
// the new state to the new state function
int* createState(int *board, int size)
{
    statesCount++;
    int org_h = heuristic(board, size);        //Heuristic of current board
    int new_h;
    int h[2] = {-1,-1};
    int temp;
    
    
    for(int i=0; i<size; i++)
    {
        temp = board[i];
        for(int j=0; j<size; j++)
        {
            board[i] = j;
            new_h = heuristic(board, size);
            if(new_h < org_h)
            {
                org_h = new_h;
                h[0] = i;
                h[1] = j;
            }
        }
        board[i]=temp;                      // Restore the original board
    }
    if(h[0] != -1 && h[1 != -1])
    {
        board[h[0]] = h[1];
        return board;
    }
    return board;
}

// The function prints the board when called
void print(int *board, int size)
{
    for (int i = 0; i < size; i++)
    {
        for (int j = 0; j < size; j++)
        {
            if (j == board[i])
            {
                cout << 1 << " ";
            }
            else
            {
                cout << 0 << " ";
            }
        }
        cout << endl;
    }
}
