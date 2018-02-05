// avl.H

#include <stdlib.h>
#include <stdio.h>

typedef int Key;

// Node in tree has key and pointer to value associated with key.
// Also contains structural components - two pointers to left and
// right subtrees.
typedef struct Node	{
	Key		key ;
	void	*value ;
	struct Node		*leftChild, *rightChild ;
    int height;
} Node ;

// Tree is basically pointer to top node in a tree.
typedef struct Tree
{
	Node *root ;
} Tree ;


// Prototypes for functions found in avl.C
/**
  * Create a new node with key = k and value = v 
  */
Node *initNode (Key k, void *v);

/**
  * Create a new tree by creating a new node with key = k and value = v and making it the root 
  */
Tree *initTree();

/** 
  * Find a node with key k in the tree.  
  * Returns NULL if not found 
  */
Node *find( Key k, Node *root );

/** Create a new node with key=k, value=v and insert it into the tree 
  * Returns 1 upon success 0 otherwise 
  */
Node* insertNode(Key k, void *v, Node *root);

/** Create a new node with key=k, value=v and insert it into the tree 
  * Returns 1 upon success 0 otherwise 
  */
void insert(Key k, void *v, Tree *t);


/**
  * Print the text representation of the tree
  */
void printTree (Node* root);

void inOrderT(Node* root);
/**
  * Returns the Maximum of two numbers 
  */
int max(int a, int b);

/** 
  * Find the parent of a node n where n->key = k
  * Returns NULL if not found
  */
Node* findParent(Key k, Node* root);




/* functions you will implement */
/**
  * Return the height of the tree (height of the root)
  */
int height(Node *root);

/**
  * Helper function for findParent
  */
Node* findParentHelper(Key k, Node* root);



Node* rotateRight(Node* root);
Node* rotateLeft(Node* root);
int getBalanceFactor(Node* root);
int calcHeight(Node* root);




