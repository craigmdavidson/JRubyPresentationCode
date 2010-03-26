class RubyPerson
  attr_writer :name
  def greet 
    "Hello #{@name}"
  end
  def valid?
    ! (@name == nil)
  end
end