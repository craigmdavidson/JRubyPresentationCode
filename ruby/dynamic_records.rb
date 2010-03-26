require 'rubygems'
require 'active_record'
  
def connect_to database, username, password
  ActiveRecord::Base.establish_connection(
    :adapter=>"jdbcmysql", :database=>database,  
    :username=>username,   :password =>password)
end
    
def declare_active_record_for entity_name
  eval %"
  class #{entity_name} < ActiveRecord::Base
  end";
end

def as_entity_name table_name
  table_name.singularize.capitalize
end

def list_table_names database_name, username, password
  query = "SELECT TABLE_NAME FROM tables where table_schema = '#{database_name}'"
  connect_to "information_schema", username, password
  tables = ActiveRecord::Base.connection().execute(query)
  table_names = Array.new
  tables.each {|i| table_names.push(i["TABLE_NAME"])}
  table_names
end

def create_active_records_for schema, username, password
  table_names = list_table_names schema, username, password
  connect_to schema, username, password
  table_names.each {|table| declare_active_record_for(as_entity_name(table))}
  return table_names
end