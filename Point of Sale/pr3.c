/*
Assginment 3
TCSS 333, Winter 2018
Name: Hop Pham
UWNetId: hopp

Extra credit: Provided an option to remove a transaction. Will promote user the option to choice.
The option will appear after user choose to view transaction and has at least one transaction.

*/
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

struct item {
    float price;
    struct item * next;
};

typedef struct item item_tag;
struct customer_tag {
  char name[50];
  struct item * myItem;
};

typedef struct customer_tag Customer;

struct transactionList {
  Customer cus;
  struct transactionList *next;
};

typedef struct transactionList *Transaction;

float totalItemPrice(item_tag * head);
item_tag *addItem(item_tag *head, float value);
float totalItemPrice(item_tag * head);
int totalTransaction(Transaction head);
void printItems(item_tag * head);
Transaction createTransaction();
Transaction addTransaction(Transaction head, Customer value);
void destroyData(Transaction head);
void printSeparator();
void printTransactionFull(Transaction head);
void printTransactionSum(Transaction head);
float askPrice();
void fillData(Transaction tr);
void removeAllItem(Transaction head);
void removeTransactionByIndex(Transaction head, int n);
void removeTransaction(Transaction tr, Transaction *Ptr);

//Creat a new Item then add to the list item at the last position
//pre: Item allocated, value = itemPrice
//post: Item list
item_tag *addItem(/*inout*/item_tag *head, /*in*/float value) {
    item_tag *temp, *p;
    temp = malloc(sizeof(item_tag)); // create a new Item with price = value and next pointing to a NULL
    temp -> price = value;
    temp -> next = NULL;
    if (NULL == head) {
        head = temp;
    } else {
        p = head;
        while (p -> next != NULL) {
            p = p -> next; //go to the last Item.
        } 
        p -> next = temp; // point the previous last Item to the new Item.
    }
    return head;
}

//Calculate the total price of customer's items
//pre: allocated item_tag
//post: total of price
float totalItemPrice(/*in*/item_tag * head) {
    item_tag * current = head;
    float total = 0;
    while (current != NULL) {
        total += current -> price;
        current = current -> next;
    }
    return total;
}

//Calculate the total price of customer's items
//pre: allocated Transaction
//post: total of transaction
int totalTransaction(/*in*/Transaction head) {
    Transaction current = head;
    float total = 0;
    while (current != NULL) {
        total ++;
        current = current -> next;
    }
    return total;
}

//Print each item of customer to standard output
//pre: allocated item_tag
//post: none
void printItems(/*in*/item_tag * head) {
    item_tag * current = head;

    while (current != NULL) {
        printf("%.2f ", current -> price);
        current = current -> next;
    }
    printf("\n");
}

//Create a new empty Transaction
//pre: none
//post: a transaction which pointing to the NULL
Transaction createTransaction() {
  Transaction temp;
  temp = (Transaction)malloc(sizeof(struct transactionList));
  temp -> next = NULL;
  return temp;
}

//Creat a new Transaction then add to the list transaction at the last position
//pre: Transaction allocated, value = Customer
//post: Transaction list
Transaction addTransaction(/*inout*/Transaction head, /*inout*/Customer value) {
  Transaction temp, p;
  temp = createTransaction(); // create a new Transaction with customer = value and next pointing to a NULL
  temp -> cus = value;
  if (NULL == head) {
    head = temp;
  } else {
    p = head;
    while (p -> next != NULL) {
      p = p -> next; //go to the last transaction.
    } 
    p -> next = temp; // point the previous last transaction to the new transaction.
  }
  return head;
}

//Destroy linked list Transaction and all data in this struct
//pre: allocated Transaction
//post: none
void destroyData(/*inout*/Transaction head) {
    Transaction current = head, next = head;

    while (current) {
        next = current->next;
        item_tag *temp;
        while (current->cus.myItem != NULL) {
            temp = current->cus.myItem;
            current->cus.myItem = current->cus.myItem -> next;
            free(temp);
        }
        free(current);
        current = next;
    }
}

//Print a separator
//pre: none
//post: none
void printSeparator() {
    printf("-------------------------------\n");
}

//Print all transaction which contents customer's name and price for the items
//by calling printItems
//pre: allocated Transaction
//post: none
void printTransactionFull(/*in*/Transaction head) {
    Transaction current = head;
    int index = 0;
    while (current != NULL) {
        printf("Name: %s\n", current->cus.name);  
        printf("Transaction index: %d, Items: ", index);              
        printItems(current->cus.myItem);
        current = current -> next;
        index++;
    }    
}

//Print all transaction which contents customer's name and price for the items by calling
//totalItemPrice
//pre: allocated Transaction
//post: none
void printTransactionSum(/*in*/Transaction head) {
    Transaction current = head;
    int index = 0;
    while (current != NULL) {
        printf("Name: %s\n", current->cus.name);                
        printf("Transaction index: %d, Total Sales: %.2f\n", index,totalItemPrice(current->cus.myItem));
        current = current -> next;
        index++;
    }    
}

//Ask user to input a float number
//pre: none
//post: float number
float askPrice() {
    float val = 0;
    scanf("%f", &val);    
    if (val <= 0 ){        
        printf("Invalid entry, try again:");
        val = askPrice();
    }   
    return val;
}

//Fill a transaction
//pre: allocated Transaction
//post: none
void fillData(/*inout*/Transaction tr) {

    int nameLength, numItem, i;
    nameLength = 0;
    numItem = 0;           
    printf("\nEnter the customer name length and number of items: ");
    scanf("%d %d", &nameLength, &numItem);
    getchar(); //clear buffer
    //repeat the request that user must input a valid value
    while (nameLength < 1 || numItem < 1 || nameLength > 49) {
        if (nameLength > 49) {
            printf("\ncustomer name length is too long, allowed 49");
        }  
        printf("\nInvalid entry, try again: ");
        scanf("%d %d", &nameLength, &numItem);
        getchar(); //clear buffer
    }//end while
    printf("Enter a name: ");
    Customer newCus;
    item_tag * newItem = malloc(sizeof(item_tag));
    scanf("%49s", newCus.name);

    printf("1 of %d: ", numItem);   
    newItem->price = askPrice();

    newCus.myItem = newItem;
    newItem->next = NULL;
    if (numItem > 1) {
	for (i = 2; i <= numItem; i++) {
            printf("%d of %d: ", i, numItem);
	    float price;    
            price = askPrice();
            newItem = addItem(newItem, price);
	}
    }

    if (NULL == tr->cus.myItem) {
        tr-> cus = newCus;
        tr -> next = NULL;
    } else {        
        tr = addTransaction(tr, newCus);
    }
}

//Remove all item of customer
//pre: allocated transaction
//post: item cleared
void removeAllItem(Transaction head) {
    item_tag *temp;
    while (head->cus.myItem != NULL) {
        temp = head->cus.myItem;
        head->cus.myItem = head->cus.myItem -> next;
        free(temp);
    }
}

//Remove the transaction
//pre: allocated transaction, the index
//post: the new transaction
void removeTransactionByIndex(/*inout*/Transaction head, /*in*/int n) {
    int i = 0;

    Transaction current = head;
    Transaction temp_node = NULL;

    if (n == 0) {
        if (totalTransaction(head) == 1) {
            removeAllItem(head);            
        } 
    } else {

        for (i = 0; i < n-1; i++) { // move to the position previous the target       
            current = current->next;
        }
        if (current->next->next == NULL) {//last transaction is the target
            removeAllItem(current->next);
            free(current->next);
            current->next = NULL; // remove targer after free item

        } else {
            temp_node = current->next;
            current->next = temp_node->next;
            removeAllItem(temp_node);
            free(temp_node);
        }
        
    }
}

//Helper method to Remove the transaction
//pre: allocated transaction
//post: the new transaction
void removeTransaction(/*inout*/Transaction tr, /*inOut*/Transaction *Ptr) {
     char choice;
     int index = -1;
     do {
         printf("Would you like to remove a transaction? y/n\n");
         scanf(" %c", &choice);         
         getchar();
     } while (choice != 'y' && choice != 'n');

     if (choice == 'y') {
         printf("Enter transaction index:");
         scanf("%d", &index);
         getchar();

         if (index > totalTransaction(tr) - 1 || index == -1) {
             printf("Invalid entry.\n");         
         } else {
             removeTransactionByIndex(tr, index);             
         }
         if (index == 0 && totalTransaction(tr) > 1) {
             removeAllItem(tr);
             *Ptr = (*Ptr)->next;
         }
     }     
}
//Main method which control the program.
//pre: none
//post: display customer and transaction
int main(void) {
     
    char choice;
    int removeIndex = -1;
    Transaction tr = createTransaction();
    tr->cus.myItem = NULL;
    tr -> next = NULL;
    choice = 'm';
    while (choice != 'q') {
        if (NULL == tr->cus.myItem) {
            printf("You have no transactions. \n");           
        } else {
            printf("You have %d transactions. \n", totalTransaction(tr));
        }
        do {
      
            printf("a to add, l to list (full), c to list (compact), q to quit.\n");
            printf("Enter your choice: ");
            scanf(" %c",&choice);
            getchar(); //clear buffer
        } while(choice != 'q' && choice != 'a' && choice != 'c' && choice != 'l');  
        

        switch (choice) {
            case 'a':     
                fillData(tr);
                printSeparator();
                break;
            case 'l':
                if (NULL == tr->cus.myItem) {
                    continue;
                }
                printTransactionFull(tr);
                removeTransaction(tr, &tr);
                printSeparator();
                break; 
            case 'c':
                if (NULL == tr->cus.myItem) {
                    continue;
                }        
                printTransactionSum(tr);
                removeTransaction(tr, &tr);
                printSeparator();
                break;
            default:      
                break;   
        }//end switch   
    }//end while
    destroyData(tr);
    return 0;
}

