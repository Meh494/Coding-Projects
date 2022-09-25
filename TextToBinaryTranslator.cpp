/************************************************************************
| Meh494                                                                |
| 2022 - 09 - 24                                                        |
| Text to Binary translator                                             |
************************************************************************/

#include <bits/stdc++.h>
using namespace std;


//Brute force coding bout to go brrrrrr


//Conversions for capital letters
char alphacap[26] = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
string acapbinary[26] = {"01000001", "01000010", "01000011", "01000100", "01000101", "01000110", "01000111", "01001000", "01001001", "01001010", "01001011", "01001100", "01001101", "01001110", "01001111", "01010000", "01010001", "01010010", "01010011", "01010100", "01010101", "01010110", "01010111", "01011000", "01011001", "01011010"};

//Conversions for lwer case letters
char alphalwr[26] = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
string alwrbinary[26] = {"01100001", "01100010", "01100011", "01100100", "01100101", "01100110", "01100111", "01101000", "01101001", "01101010", "01101011", "01101100", "01101101", "01101110", "01101111", "01110000", "01110001", "01110010", "01110011", "01110100", "01110101", "01110110", "01110111", "01111000", "01111001", "01111010"};

int main() {

    //Input output optimization. Can't hurt to have the computer have a less chance of exploding right?
    cin.sync_with_stdio(0);
    cin.tie(0);


    cout<<"Welcome to Meh494's text to binary translator! Enter any sentence you want: "<<endl;

    //Input text
    string input;
    getline(cin, input);

    //Vector where the binary bits will be stored
    vector <string> sysout;

    //Nested for loop rip

    //First loop is to iterate through all chars in the input, this includes spaces
    for(int i = 0; i<input.length(); i++){

        //capital
        if(isupper(input[i])){
            //Cycles through alphabet array to find the index, then stores the corresponding binary value in the vector
            for(int j = 0; j<26; j++){
                if(alphacap[j] == input[i]){
                    sysout.push_back(acapbinary[j]);
                    break;
                }
            }
        }

        //lowercase
        else if(islower(input[i])){

             //Same thing as capital version
             for(int j = 0; j<26; j++){
                if(alphalwr[j] == input[i]){
                    sysout.push_back(alwrbinary[j]);
                    break;
                }
            }
        }
        //space
        else if (input[i] == ' '){
            sysout.push_back("00100000");
        }
        //?
        else if (input[i] == '?'){
            sysout.push_back("00111111");
        }
        //.
        else if (input[i] == '.'){
            sysout.push_back("00101110");
        }
        //,
        else if (input[i] == ','){
            sysout.push_back("00101100");
        }
       // !
        else if (input[i] == '!'){
            sysout.push_back("00100001");
        }

    }


    int c = 0;

    //Prints out each element of the vector
    for(auto i : sysout){
        cout<<i<<" ";

        //Just a little counter to format the output a bit nicer.
        c++;
        if(c == 5){
            cout<<endl;
            c = 0;
        }

    }
    cout<<endl;
    cout<<"Thanks for using Meh494's text to binary translator!"<<endl;
}
