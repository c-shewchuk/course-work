#include "bst.h"

// Code for ELEC278 Lab 3.  Some code has already been implemented.
// You will develop your own code - look for the comments.

Node *initNode (Key k, void *v)
// Allocate memory for a new node and initialize fields.
// Returns pointer to node created.
{
	Node *n = malloc(sizeof(Node));
	n->key = k;
	n->value = v;
    n->leftChild = NULL;
    n->rightChild = NULL;
	return n;

}

Tree *initTree(Key k, void *v)
// Set up a new tree. Allocates memory for the Tree structure,
// then calls initNode() to allocate the first node.
{
	Tree* t = malloc(sizeof(Tree));
	t->root = initNode(k,v);
	return t;
}

Node *find( Key k, Node *root )
{
	// termination condition #1
	if (root == NULL)
		return root;
	// termination condition #2
	else if (root->key == k)
		return root;
	else if (k > root->key)
		return find(k,root->rightChild);
	else
		return find(k,root->leftChild);

}

int insert(Key k, void *v, Node *root)
{
	int result = 0;
	// this if statement can only become true with the first root (root of whole tree)
	if (root == NULL)
	{
		Node *n = initNode(k,v);
		root = n;
		result = 1;
	}
	/* already exists in tree ==> can't insert */
	if (root->key == k)
		result = 0;
	else if (k < root->key)
	{
		if (root->leftChild != NULL)
			insert(k, v, root->leftChild);
		else
		{
			Node *n = initNode(k,v);
			root->leftChild = n;
			result = 1;
		}
	}

	else if (k > root->key)
	{
		if (root->rightChild != NULL)
			insert(k,v,root->rightChild);
		else
		{
			Node*n = initNode(k,v);
			root->rightChild = n;
			result = 1;
		}
	}

	return result;
}

void printTree (Node* root)
{
	// assume printTree magically know the types in the tree node
	printf("{");
	printf("(%ld,%d)",root->key,*(int*)root->value); // need to cast the void pointer first
	printf(",");

	// left child.  if its NULL it will just print two empty braces
	if (root->leftChild)
	{
		printTree(root->leftChild);
	}
	else
		printf("{}");

	printf(",");

	// right child.  if its NULL it will just print two empty braces
	if (root->rightChild)
	{
		printTree(root->rightChild);
	}
	else
		printf("{}");

	printf("}");
}

//int max(int a, int b) {if(a>=b) return a; else return b;}

int height(Node *root)
	{
	int left, right;
	if(root == NULL){
	 return 0;
	}
	else{
		left=height(root->leftChild);
		right=height(root->rightChild);
	}
	//if(left>right)
		//return (left+1);
	//else
		//return (right+1);
	return (max(left, right)+1);
}


Node* findParentHelper (Key k, Node* root)
{
	if (root->leftChild){
		if (root->leftChild->key == k)
			return root;
		}
	if (root->rightChild){
		if (root->rightChild->key == k)
			return root;}
	if(k<root->key)
		return findParentHelper(k, root->leftChild);
	else
		return findParentHelper(k, root->rightChild);
	}

Node* findParent(Key k, Node* root)
{
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

void delete (Node* p, Node *m)
{
	if(m->leftChild==NULL && m->rightChild==NULL){
		if(m->key < p->key){
			p->leftChild = NULL;
			free(m);
			return;}
		else if(m->key > p->key){
			p->rightChild = NULL;
			free(m);}
		else{
			if(p->rightChild){
			 	if(m->key == p->rightChild->key)
					p->rightChild = NULL;
				else
					p->leftChild = NULL;
			}
			else
				p->leftChild = NULL;
			}

			return;}

		else if(m->rightChild){
			Node* a;
			Node* b;
			b = m;
			a = m->rightChild;
			while(a->leftChild){
				b = a;
				a = a->leftChild;
			}
			m->key = a->key;
			delete(b, a);
		}
		else{
			Node* a;
			Node* b;
			b = m;
			a = m->leftChild;
			while(a->rightChild){
				b = a;
				a = a->rightChild;
			}
			m->key = a->key;
			delete(b, a);
		}
}

int withdraw(Key k, Node* root, Node* n)
{
	Node *p, *m;
	m = find(k, root);

	if (m) /* another way or writing if (m!==NULL) */
	{
		/* create a copy of the node with the same key and value*/
		n = initNode(m->key,m->value);
		p = findParent(k,root);
		/* now I can delete the node */
		delete(p,m);
		return 1;
	}

	return 0;
}
