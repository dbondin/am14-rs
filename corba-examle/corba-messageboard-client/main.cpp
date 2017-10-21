#include <iostream>

#include "message_board.hh"

int main(int argc, char ** argv) {

  if(argc < 2) {
    std::cerr<<"IOR parameter required"<<std::endl;
    return 1;
  }
  else if(argc < 3) {
	    std::cerr<<"FROM parameter required"<<std::endl;
	    return 1;
  }
  else if(argc < 4) {
	    std::cerr<<"MESSAGE parameter required"<<std::endl;
	    return 1;
  }

  CORBA::ORB_ptr orb = CORBA::ORB_init(argc, argv, "omniORB4");

  CORBA::Object_var obj = orb->string_to_object(argv[1]);

  MessageBoard::MessagePostBox_var e = MessageBoard::MessagePostBox::_narrow(obj);

  std::cout<<"Server version: "<<e->getVersion()<<std::endl;

  e->putMessage(argv[2], argv[3]);

  return 0;
}
