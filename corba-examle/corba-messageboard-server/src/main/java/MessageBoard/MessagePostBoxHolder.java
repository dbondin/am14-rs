package MessageBoard;

/**
* MessageBoard/MessagePostBoxHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from message_board.idl
* Saturday, October 21, 2017 8:15:54 AM MSK
*/

public final class MessagePostBoxHolder implements org.omg.CORBA.portable.Streamable
{
  public MessageBoard.MessagePostBox value = null;

  public MessagePostBoxHolder ()
  {
  }

  public MessagePostBoxHolder (MessageBoard.MessagePostBox initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = MessageBoard.MessagePostBoxHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    MessageBoard.MessagePostBoxHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return MessageBoard.MessagePostBoxHelper.type ();
  }

}
