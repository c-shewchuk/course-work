#include "avl.h"

// Code for ELEC278 Lab 4.  Some code has already been implemented.
// You will develop your own code - look for the comments.

Node *initNode(Key k, void *v)
// Allocate memory for a new node and initialize fields.
// Returns pointer to node created.
{
	Node *n = malloc(sizeof(Node));
	n->key = k;
	n->value = v;
	n->leftChild = NULL;
	n->rightChild = NULL;
	n->height = -1;
	return n;

}

Tree *initTree()
// Set up a new tree. Allocates memory for the Tree structure,
// then calls initNode() to allocate the first node.
{
	Tree* t = malloc(sizeof(Tree));
	t->root = NULL;
	return t;
}

Node *find(Key k, Node *root) {
	// termination condition #1
	if (root == NULL)
		return root;
	// termination condition #2
	else if (root->key == k)
		return root;
	else if (k > root->key)
		return find(k, root->rightChild);
	else
		return find(k, root->leftChild);

}

void insert(Key k, void *v, Tree *t) {
	printf("Inserting %d\n", k);
	if (t->root == NULL) {
		Node *n = initNode(k, v);
		n->height = 0;
		t->root = n;
		return;
	}
	t->root = insertNode(k, v, t->root);
}
Node* rotateRight(Node* root) {
	printf("Rotate Right\n");
Node * temp= root->leftChild;

	if(temp->rightChild){
		root->leftChild = temp->rightChild;
	}
	temp->rightChild = root;
	root = temp;
	root->height++;
	root->rightChild->height--;
	return root;
}
Node* rotateLeft(Node* root) {
	printf("Rotate Left\n");
	Node * temp= root->rightChild;

		if(temp->leftChild){
			root->rightChild = temp->leftChild;
		}
		temp->leftChild = root;
		root = temp;
		root->height++;
		root->leftChild->height--;
		return root;

}
int getBalanceFactor(Node* root) {
    //Write code to return the balance factor of root
		if(root->leftChild == NULL && root->rightChild == NULL){
	return 0;
	}
	else if (root->leftChild == NULL){
		return calcHeight(root->rightChild) +1;
	}
	else if (root->rightChild ==  NULL){
		return calcHeight(root->leftChild) +1;
	}
int hrl = calcHeight(root->rightChild);
int hll = calcHeight(root->leftChild);
int bf = hll - hrl;
}

int calcHeight(Node* root){
    //Write code to return the height of root using the heights of left &right children
		int hrl = -1, hll = -1;

		if(root->leftChild){
			hll = root->leftChild->height;
		}
		if(root->rightChild){
			hrl = root->rightChild->height;
		}
		if(hrl > hll)
		return hrl + 1;
		else
		return hll +1;
}
Node* rebalance(Node* root) {
    // use getBalanceFactor to check if root is unbalanced.
    // if unbalanced, find out what type of rotations are needed
    // print the key of the node and the type of rotation
    // e.g. printf("Node %d is unbalanced. Left of Left: ", root->key);
    // return the new root.

int bf = getBalanceFactor(root);
if(bf<-1){
bf = getBalanceFactor(root->rightChild);
	if(bf>0)
	rotateRight(root->rightChild);
	else
	rotateLeft(root);
}
	else if(bf>1){
	bf = getBalanceFactor(root->leftChild);
	if(bf<0)
	rotateLeft(root->leftChild);
	rotateRight(root);
}
	else
		rotateRight(root);


    //if balanced then return root

    return root;

}

Node* insertNode(Key k, void *v, Node *root) {

	/* already exists in tree ==> can't insert */
	if (!root) {
		Node *n = initNode(k, v);
		n->height = 0;
		return n;
	}
	if (k < root->key) {
		root->leftChild = insertNode(k, v, root->leftChild);
		root->height = calcHeight(root);
	}

	else if (k > root->key) {
		root->rightChild = insertNode(k, v, root->rightChild);
		root->height = calcHeight(root);
	}

	return rebalance(root);
}

void inOrderT(Node* root) {
	if (root) {
		inOrderT(root->leftChild);
		printf("%d(h=%d,bf=%d) - ", root->key, root->height,getBalanceFactor(root));
		inOrderT(root->rightChild);
	}
}

void printTree(Node* root) {
	// assume printTree magically know the types in the tree node
	printf("{");
	printf("(%ld,%d)", (long) root->key, *(int*) root->value); // need to cast the void pointer first
	printf(",");

	// left child.  if its NULL it will just print two empty braces
	if (root->leftChild) {
		printTree(root->leftChild);
	} else
		printf("{}");

	printf(",");

	// right child.  if its NULL it will just print two empty braces
	if (root->rightChild) {
		printTree(root->rightChild);
	} else
		printf("{}");

	printf("}");
}

int max(int a, int b) {
	if (a >= b)
		return a;
	else
		return b;
}


int height(Node *root) {
	//termination condition: reached a leaf
	if (root->leftChild == NULL && root->rightChild == NULL)
		return 0; // height of leaf is zero
	else if (!root->rightChild) /* only left child */
		return 1 + height(root->leftChild);
	else if (!root->leftChild) /* only right child */
		return 1 + height(root->rightChild);
	else
		return 1 + max(height(root->leftChild), height(root->rightChild));
}

Node* findParentHelper(Key k, Node* root) {
	/* your code goes here    <<<<<<<<<<<<<<<*/
	if (!root)
		return NULL;

	if (root->leftChild != NULL && root->leftChild->key == k)
		return root;
	if (root->rightChild != NULL && root->rightChild->key == k)
		return root;

	if (root->key > k)
		return findParentHelper(k, root->leftChild);
	if (root->key < k)
		return findParentHelper(k, root->rightChild);
	return NULL;
}

Node* findParent(Key k, Node* root) {
	/* Deal with special special cases which could only happen for the root of the whole tree */
	if (root == NULL)
		return root;
	/* real root doesn't have a parent so we make it the parent of itself */
	if (root->key == k)
		return root;
	/* root has no children */
	if (!(root->leftChild) && !(root->rightChild))
		return NULL;

	/* Deal with the cases where the root has at least one child */
	return findParentHelper(k, root);
}
