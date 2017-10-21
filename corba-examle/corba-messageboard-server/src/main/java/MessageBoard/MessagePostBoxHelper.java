package MessageBoard;


/**
* MessageBoard/MessagePostBoxHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from message_board.idl
* Saturday, October 21, 2017 8:15:54 AM MSK
*/

abstract public class MessagePostBoxHelper
{
  private static String  _id = "IDL:MessageBoard/MessagePostBox:1.0";

  public static void insert (org.omg.CORBA.Any a, MessageBoard.MessagePostBox that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static MessageBoard.MessagePostBox extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (MessageBoard.MessagePostBoxHelper.id (), "MessagePostBox");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static MessageBoard.MessagePostBox read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_MessagePostBoxStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, MessageBoard.MessagePostBox value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static MessageBoard.MessagePostBox narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof MessageBoard.MessagePostBox)
      return (MessageBoard.MessagePostBox)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      MessageBoard._MessagePostBoxStub stub = new MessageBoard._MessagePostBoxStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static MessageBoard.MessagePostBox unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof MessageBoard.MessagePostBox)
      return (MessageBoard.MessagePostBox)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      MessageBoard._MessagePostBoxStub stub = new MessageBoard._MessagePostBoxStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
