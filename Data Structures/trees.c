#include <stdlib.h>
#include <stdio.h>

typedef struct NodeT{
  struct NodeT* left;
  struct NodeT* right;
  char value;
  int key;
}NodeT

typedef struct BST{
  NodeT* root;
}BST;

void preorderT(Node* n){
if(!root)
  return;
  printf("%d\n",root->key);
  preorderT(root->left);
  preorderT(root->right);

}

void postorderT(){
  if(!root)
  return;

  postorderT(root->left);
  postorderT(root->right);
  printf("%d\n",root->key);

}

void inorderT(){
  if(!root)
  return;
    inOrder(root->left);
    printf("%d", root->key);
    inOrder(root->right);
}

void insert(BST *tree, int x){
  NodeT* newNode = malloc(sizeof(int));
  if(!tree->root){
    tree->root = newNode;
  }
  insertR(tree->root, newNode);

}
//write these iteratively as well
void insertR(NodeT* root, NodeT* newNode){
  if(!root == NULL){
    return;
  }
  if(newNode->key <root->key){
    if(newNode->key !NULL){
  insertR(root->left,newNode);
}
else
root->left = newNode;
  else if (newNode->key >root->key)
  insertR(root->right,newNode);{
    if (newNode->key !NULL){
      insert
    }
  }
}
}
}
//
  void destruct(Node* root){
    if(root){
      destruct(root->left);
      destruct(root->right);

      free(root); // recursive post order traversal and destruction
    }
  }

  void print_tree(Node* root){ //Prints tree in order, I think
    //Check if root points to NULL
    if(!root)
    print_tree(root->left);
    printf("%s", root->key);
    print_tree(root->right);
  }

int countleaves(Node* root){
  if(!root)
  return 0;
  int count = 0;
  count = countleaves(root->left; //Stops here and goes to the call of the function at the top from the leftChild
  count = countleaves(root->right);// after performing step 8 and returning the increase in the sum, does the right child
  if(root->right == NULL && root->left ==  NULL){ // the check if the Function should continue
    count++;

  }
  return count;
}

int deleteAllLeaves(Node* root){
  if(!root)
  return 0;

  if(root->left == NULL && root->right == NULL){
  free(root);
  return 0;
}
if(deleteAllLeaves(root->left))
root->left = NULL;
if(deleteAllLeaves(root->right));
root->right = NULL;
return 1;
}
int main(){



}
