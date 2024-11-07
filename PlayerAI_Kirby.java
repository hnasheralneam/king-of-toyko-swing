import java.util.*;
import java.util.ArrayList;

public class PlayerAI_Kirby extends Player {
    // String name;
    boolean initial; // might need to keep
    int id;


// For other game classes
/*
public PlayerAI_Kirby() {
    super();
}*/
public PlayerAI_Kirby(int id) {
    this.playerType = "Kirby";
    this.id = id;
}



public boolean[] rerollDice(int turn, int currentPlayer, int inTokyo, int[] gameDice, int[] playerHealth, int[] playerFame) {
boolean[] rerollDice = {true, true, true, true, true, true};
int extraCount = 0;
int twoCount = 0;
int threeCount = 0;
// if in tokyo
boolean isInTokyo = (inTokyo == currentPlayer);

for (int i = 0; i < gameDice.length; i++) {
// count 4
if(gameDice[i] == 4){
extraCount++;
}
// If health is less then 4 -> go for health

//Keep fame

// count 2 and 3
for (int j = 0; j < 6; j++){
if(gameDice[j] == 2){
twoCount++;
}
if(gameDice[j] == 3){
threeCount++;
}
}
if (playerHealth[currentPlayer] <= 3){
if(gameDice[i] == 5){ //Heal if low health
rerollDice[i] = false;
}
}
// first three then two
if (threeCount >= 2){
int count = 0;
for (int j = 0; j < 6; j++){
if(gameDice[j] == 3){
rerollDice[j] = false;
count++;
}
if(count == 3){
if(playerFame[currentPlayer] >= 17){
return rerollDice;
}
break;
}
}
} else {
if (twoCount >= 2){
int count = 0;
for (int j = 0; j < 6; j++){
if(gameDice[j] == 2){
rerollDice[j] = false;
count++;
}
if(count == 3){
break;
}
}
}
}

// Damage condition
if (inTokyo != -1 && (isInTokyo || (!isInTokyo && playerFame[inTokyo] >= 15) && (gameDice[i] == 6)) ) {
for(int k = 0; k < rerollDice.length; k++){
if(gameDice[k] != 5){
rerollDice[k] = true;
}
}
rerollDice[i] = false; // Keep attack dice if in Tokyo
}


}


// extra turn
if (extraCount >= 3){
int count = 0;
for (int j = 0; j < 6; j++){
if(gameDice[j] == 4){
rerollDice[j] = false;
count++;
}
if(count == 3){
break;
}
}
}
return rerollDice;

}

public boolean leaveTokyo(int turn, int currentPlayer, int inTokyo, int[] gameDice, int[] playerHealth, int[] playerFame) {
// always leave tokyo
boolean isDead = false;

if(playerFame[currentPlayer] >= 18 && playerHealth[currentPlayer] > 1){
return false;
}

for (int i = 0; i < 2; i++){
if (playerHealth[i] <= 0){
isDead = true;
}
}
return !isDead;

}

public String toString() {
return "PlayerAI_Kirby";
}
}